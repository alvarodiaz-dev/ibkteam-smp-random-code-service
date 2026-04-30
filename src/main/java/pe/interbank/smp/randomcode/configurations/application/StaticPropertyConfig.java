package pe.interbank.smp.randomcode.configurations.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class StaticPropertyConfig {

    private final long codeGenerationTimeout;

    public StaticPropertyConfig(
            @Value("${static.code-generation-timeout:5000}") long codeGenerationTimeout) {
        this.codeGenerationTimeout = codeGenerationTimeout;
    }
}