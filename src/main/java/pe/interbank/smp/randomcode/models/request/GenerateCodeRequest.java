package pe.interbank.smp.randomcode.models.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCodeRequest {
    private Boolean includeTimestamp;
    private String format;
}