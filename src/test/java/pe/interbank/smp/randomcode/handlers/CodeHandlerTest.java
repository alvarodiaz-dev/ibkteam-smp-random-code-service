package pe.interbank.smp.randomcode.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;
import pe.interbank.smp.randomcode.services.CodeService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodeHandlerTest {

    @Mock
    private CodeService codeService;

    @InjectMocks
    private CodeHandler codeHandler;

    private CodeResponse mockCodeResponse;
    private HealthResponse mockHealthResponse;

    @BeforeEach
    void setUp() {
        mockCodeResponse = CodeResponse.builder()
                .code("4827")
                .timestamp("2025-01-15T10:30:45.123Z")
                .requestId("req-a1b2c3d4-e5f6")
                .build();

        mockHealthResponse = HealthResponse.builder()
                .status("UP")
                .timestamp("2025-01-15T10:30:45.123Z")
                .service("random-code-service")
                .version("1.0.0")
                .build();
    }

    @Test
    void generateCode_ValidRequest_ReturnsOk() {
        when(codeService.generateCode(any(HttpHeaders.class)))
                .thenReturn(Mono.just(mockCodeResponse));

        MockServerRequest serverRequest = MockServerRequest.builder()
                .headers(HttpHeaders.empty())
                .build();

        StepVerifier.create(codeHandler.generateCode(serverRequest))
                .assertNext(response -> {
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
                })
                .verifyComplete();
    }

    @Test
    void generateCode_ServiceFails_Returns500() {
        when(codeService.generateCode(any(HttpHeaders.class)))
                .thenReturn(Mono.error(new RuntimeException("Service unavailable")));

        MockServerRequest serverRequest = MockServerRequest.builder()
                .headers(HttpHeaders.empty())
                .build();

        StepVerifier.create(codeHandler.generateCode(serverRequest))
                .assertNext(response -> {
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                })
                .verifyComplete();
    }

    @Test
    void healthCheck_ServiceUp_ReturnsOk() {
        when(codeService.healthCheck())
                .thenReturn(Mono.just(mockHealthResponse));

        MockServerRequest serverRequest = MockServerRequest.builder()
                .build();

        StepVerifier.create(codeHandler.healthCheck(serverRequest))
                .assertNext(response -> {
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
                })
                .verifyComplete();
    }

    @Test
    void healthCheck_ServiceDown_Returns503() {
        HealthResponse downResponse = HealthResponse.builder()
                .status("DOWN")
                .timestamp("2025-01-15T10:30:45.123Z")
                .service("random-code-service")
                .version("1.0.0")
                .error("Service initialization failed")
                .build();

        when(codeService.healthCheck())
                .thenReturn(Mono.just(downResponse));

        MockServerRequest serverRequest = MockServerRequest.builder()
                .build();

        StepVerifier.create(codeHandler.healthCheck(serverRequest))
                .assertNext(response -> {
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
                })
                .verifyComplete();
    }
}