package pe.interbank.smp.randomcode.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.interbank.smp.randomcode.configurations.application.PropertiesConfig;

import java.util.Arrays;

@Slf4j
@Configuration
public class BeanInjectionConfig {

    @Autowired
    private PropertiesConfig propertiesConfig;

    public boolean isMock(String... mocks) {
        if (propertiesConfig == null || propertiesConfig.getListMocks() == null) {
            return false;
        }
        return Arrays.stream(mocks).anyMatch(x -> propertiesConfig.getListMocks().contains(x));
    }
}