package pe.interbank.smp.randomcode.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pe.interbank.smp.randomcode.constants.AppConstants;
import pe.interbank.smp.randomcode.models.request.GenerateCodeRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;
import pe.interbank.smp.randomcode.services.CodeService;
import pe.interbank.smp.randomcode.utils.CodeGenerator;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeGenerator codeGenerator;

    @Override
    public Mono<CodeResponse> generateCode(HttpHeaders headers) {
        log.info("CodeServiceImpl - generateCode: Generating random code");
        
        String code = codeGenerator.generateCode();
        String timestamp = codeGenerator.generateTimestamp();
        String requestId = codeGenerator.generateRequestId();
        
        CodeResponse response = CodeResponse.builder()
                .code(code)
                .timestamp(timestamp)
                .requestId(requestId)
                .build();
        
        log.info("CodeServiceImpl - generateCode: Code generated successfully - {}", requestId);
        return Mono.just(response);
    }

    @Override
    public Mono<CodeResponse> generateCodeWithOptions(GenerateCodeRequest request, HttpHeaders headers) {
        log.info("CodeServiceImpl - generateCodeWithOptions: Generating code with options");
        
        String code = codeGenerator.generateCode();
        String timestamp = request.getIncludeTimestamp() != null && request.getIncludeTimestamp() 
                ? codeGenerator.generateTimestamp() 
                : null;
        String requestId = codeGenerator.generateRequestId();
        
        CodeResponse.CodeResponseBuilder responseBuilder = CodeResponse.builder()
                .code(code)
                .requestId(requestId);
        
        if (timestamp != null) {
            responseBuilder.timestamp(timestamp);
        }
        
        log.info("CodeServiceImpl - generateCodeWithOptions: Code generated successfully - {}", requestId);
        return Mono.just(responseBuilder.build());
    }

    @Override
    public Mono<HealthResponse> healthCheck() {
        log.info("CodeServiceImpl - healthCheck: Performing health check");
        
        HealthResponse response = HealthResponse.builder()
                .status("UP")
                .timestamp(codeGenerator.generateTimestamp())
                .service(AppConstants.SERVICE_NAME)
                .version(AppConstants.SERVICE_VERSION)
                .build();
        
        return Mono.just(response);
    }
}