package pe.interbank.smp.randomcode.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CodeGenerator {

    private static final int MIN_CODE = 1000;
    private static final int MAX_CODE = 9999;

    public String generateCode() {
        int randomCode = (int) (Math.random() * (MAX_CODE - MIN_CODE + 1)) + MIN_CODE;
        return String.format("%04d", randomCode);
    }

    public String generateRequestId() {
        return "req-" + UUID.randomUUID().toString().substring(0, 12);
    }

    public String generateTimestamp() {
        return Instant.now().toString();
    }
}