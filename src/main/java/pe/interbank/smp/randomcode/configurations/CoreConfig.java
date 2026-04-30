package pe.interbank.smp.randomcode.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CoreConfig {
    // Central application configuration
}