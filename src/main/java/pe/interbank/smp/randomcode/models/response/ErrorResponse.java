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
@Schema(description = "Error response")
public class ErrorResponse {

    @Schema(description = "Error type", example = "Internal server error")
    @JsonProperty("error")
    private String error;

    @Schema(description = "Descriptive error message", example = "Failed to generate code")
    @JsonProperty("message")
    private String message;

    @Schema(description = "ISO 8601 timestamp", example = "2025-01-15T10:30:45.123Z")
    @JsonProperty("timestamp")
    private String timestamp;

    @Schema(description = "Request ID that failed", example = "req-a1b2c3d4-e5f6")
    @JsonProperty("requestId")
    private String requestId;
}