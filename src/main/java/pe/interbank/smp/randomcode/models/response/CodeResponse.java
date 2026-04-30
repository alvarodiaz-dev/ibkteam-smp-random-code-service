package pe.interbank.smp.randomcode.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing the generated random code")
public class CodeResponse {

    @Schema(description = "Random 4-digit code (1000-9999)", example = "4827")
    @JsonProperty("code")
    private String code;

    @Schema(description = "ISO 8601 timestamp of generation", example = "2025-01-15T10:30:45.123Z")
    @JsonProperty("timestamp")
    private String timestamp;

    @Schema(description = "Unique request identifier for traceability", example = "req-a1b2c3d4-e5f6")
    @JsonProperty("requestId")
    private String requestId;
}