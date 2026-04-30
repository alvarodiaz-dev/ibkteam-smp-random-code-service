package pe.interbank.smp.randomcode.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import pe.interbank.smp.randomcode.models.request.GenerateCodeRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;
import pe.interbank.smp.randomcode.services.impl.CodeServiceImpl;
import pe.interbank.smp.randomcode.utils.CodeGenerator;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodeServiceTest {

    @Mock
    private CodeGenerator codeGenerator;

    @InjectMocks
    private CodeServiceImpl codeService;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.add("X-Correlation-Id", "test-correlation-id");
        headers.add("SESSION_ID", "test-session-id");
    }

    @Test
    void generateCode_ReturnsValidResponse() {
        when(codeGenerator.generateCode()).thenReturn("4827");
        when(codeGenerator.generateTimestamp()).thenReturn("2025-01-15T10:30:45.123Z");
        when(codeGenerator.generateRequestId()).thenReturn("req-a1b2c3d4-e5f6");

        StepVerifier.create(codeService.generateCode(headers))
                .assertNext(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getCode()).isEqualTo("4827");
                    assertThat(response.getTimestamp()).isEqualTo("2025-01-15T10:30:45.123Z");
                    assertThat(response.getRequestId()).isEqualTo("req-a1b2c3d4-e5f6");
                })
                .verifyComplete();
    }

    @Test
    void healthCheck_ReturnsUpStatus() {
        when(codeGenerator.generateTimestamp()).thenReturn("2025-01-15T10:30:45.123Z");

        StepVerifier.create(codeService.healthCheck())
                .assertNext(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getStatus()).isEqualTo("UP");
                    assertThat(response.getService()).isEqualTo("random-code-service");
                    assertThat(response.getVersion()).isEqualTo("1.0.0");
                })
                .verifyComplete();
    }
}