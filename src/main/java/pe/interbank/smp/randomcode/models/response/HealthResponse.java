package pe.interbank.smp.randomcode.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Health check response")
public class HealthResponse {

    @Schema(description = "Service status (UP/DOWN)", example = "UP")
    @JsonProperty("status")
    private String status;

    @Schema(description = "ISO 8601 timestamp", example = "2025-01-15T10:30:45.123Z")
    @JsonProperty("timestamp")
    private String timestamp;

    @Schema(description = "Service name", example = "random-code-service")
    @JsonProperty("service")
    private String service;

    @Schema(description = "Service version", example = "1.0.0")
    @JsonProperty("version")
    private String version;

    @Schema(description = "Error detail (only when status=DOWN)", example = "Service initialization failed")
    @JsonProperty("error")
    private String error;
}