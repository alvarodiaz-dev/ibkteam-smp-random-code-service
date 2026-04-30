package pe.interbank.smp.randomcode.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.interbank.smp.randomcode.constants.AppConstants;
import pe.interbank.smp.randomcode.models.request.GenerateCodeRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.ErrorResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;
import pe.interbank.smp.randomcode.services.CodeService;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeHandler {

    private final CodeService codeService;

    public Mono<ServerResponse> generateCode(ServerRequest serverRequest) {
        log.info("CodeHandler - generateCode: Start processing GET /api/v1/codes request");
        var headers = serverRequest.headers().asHttpHeaders();

        return codeService.generateCode(headers)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(this::errorHandling);
    }

    public Mono<ServerResponse> generateCodePost(ServerRequest serverRequest) {
        log.info("CodeHandler - generateCodePost: Start processing POST /api/v1/codes/generate request");
        var headers = serverRequest.headers().asHttpHeaders();

        return serverRequest.bodyToMono(GenerateCodeRequest.class)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("CodeHandler - generateCodePost: Empty request body, using defaults");
                    return Mono.just(GenerateCodeRequest.builder()
                            .includeTimestamp(true)
                            .build());
                }))
                .flatMap(request -> codeService.generateCodeWithOptions(request, headers))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(this::errorHandling);
    }

    public Mono<ServerResponse> healthCheck(ServerRequest serverRequest) {
        log.info("CodeHandler - healthCheck: Start processing GET /health request");

        return codeService.healthCheck()
                .flatMap(response -> {
                    if ("UP".equals(response.getStatus())) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response);
                    } else {
                        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response);
                    }
                })
                .onErrorResume(throwable -> {
                    log.error("CodeHandler - healthCheck: Error during health check - {}", throwable.getMessage());
                    HealthResponse errorResponse = HealthResponse.builder()
                            .status("DOWN")
                            .timestamp(java.time.Instant.now().toString())
                            .service(AppConstants.SERVICE_NAME)
                            .version(AppConstants.SERVICE_VERSION)
                            .error(throwable.getMessage())
                            .build();
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorResponse);
                });
    }

    private Mono<ServerResponse> errorHandling(Throwable e) {
        log.error("CodeHandler - Error: {}", e.getMessage());
        String requestId = "req-" + java.util.UUID.randomUUID().toString().substring(0, 12);
        String timestamp = java.time.Instant.now().toString();

        if (e instanceof IllegalArgumentException) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(ErrorResponse.builder()
                            .error("Bad Request")
                            .message(e.getMessage())
                            .timestamp(timestamp)
                            .requestId(requestId)
                            .build());
        }

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ErrorResponse.builder()
                        .error("Internal server error")
                        .message(e.getMessage() != null ? e.getMessage() : "Error processing request")
                        .timestamp(timestamp)
                        .requestId(requestId)
                        .build());
    }
}