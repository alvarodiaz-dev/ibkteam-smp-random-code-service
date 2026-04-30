package pe.interbank.smp.randomcode.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGeneratorTest {

    private CodeGenerator codeGenerator;

    @BeforeEach
    void setUp() {
        codeGenerator = new CodeGenerator();
    }

    @Test
    void generateCode_Returns4DigitCode() {
        String code = codeGenerator.generateCode();
        
        assertThat(code).isNotNull();
        assertThat(code).hasSize(4);
        assertThat(code).matches("^[0-9]{4}$");
    }

    @Test
    void generateRequestId_StartsWithReqPrefix() {
        String requestId = codeGenerator.generateRequestId();
        
        assertThat(requestId).isNotNull();
        assertThat(requestId).startsWith("req-");
    }

    @Test
    void generateTimestamp_ReturnsValidIso8601Format() {
        String timestamp = codeGenerator.generateTimestamp();
        
        assertThat(timestamp).isNotNull();
        assertThat(timestamp).matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?Z$");
    }
}