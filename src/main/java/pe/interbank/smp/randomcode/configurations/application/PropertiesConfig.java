package pe.interbank.smp.randomcode.configurations.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "common")
public class PropertiesConfig {
    private String profile;
    private String mocks;

    public List<String> getListMocks() {
        return mocks != null ? Arrays.asList(mocks.split(",")) : List.of();
    }

    public List<String> getProfilesList() {
        return profile != null ? Arrays.asList(profile.split(",")) : List.of();
    }
}