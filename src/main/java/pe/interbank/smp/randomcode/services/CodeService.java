package pe.interbank.smp.randomcode.services;

import org.springframework.http.HttpHeaders;
import pe.interbank.smp.randomcode.models.request.GenerateCodeRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;
import reactor.core.publisher.Mono;

public interface CodeService {
    
    Mono<CodeResponse> generateCode(HttpHeaders headers);
    
    Mono<CodeResponse> generateCodeWithOptions(GenerateCodeRequest request, HttpHeaders headers);
    
    Mono<HealthResponse> healthCheck();
}