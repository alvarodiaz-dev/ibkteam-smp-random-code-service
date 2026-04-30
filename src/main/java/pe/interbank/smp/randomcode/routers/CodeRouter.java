package pe.interbank.smp.randomcode.routers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.interbank.smp.randomcode.constants.AppConstants;
import pe.interbank.smp.randomcode.handlers.CodeHandler;
import pe.interbank.smp.randomcode.models.request.GenerateCodeRequest;
import pe.interbank.smp.randomcode.models.response.CodeResponse;
import pe.interbank.smp.randomcode.models.response.HealthResponse;

@Configuration
@RequiredArgsConstructor
public class CodeRouter {

    private final CodeHandler codeHandler;

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = AppConstants.EndPoints.GENERATE_CODE,
            method = RequestMethod.GET,
            beanClass = CodeHandler.class,
            beanMethod = "generateCode",
            operation = @Operation(
                operationId = "generateCode",
                summary = "Generate random 4-digit code",
                description = "Generates and returns a random 4-digit numeric code (1000-9999)",
                tags = {"codes"},
                responses = {
                    @ApiResponse(responseCode = "200", description = "Code generated successfully",
                        content = @Content(schema = @Schema(implementation = CodeResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
                }
            )
        ),
        @RouterOperation(
            path = AppConstants.EndPoints.GENERATE_CODE_POST,
            method = RequestMethod.POST,
            beanClass = CodeHandler.class,
            beanMethod = "generateCodePost",
            operation = @Operation(
                operationId = "generateCodePost",
                summary = "Generate code (POST alternative)",
                description = "POST alternative for code generation with optional parameters",
                tags = {"codes"},
                requestBody = @RequestBody(
                    required = false,
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = GenerateCodeRequest.class)
                    )
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Code generated successfully",
                        content = @Content(schema = @Schema(implementation = CodeResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
                }
            )
        ),
        @RouterOperation(
            path = AppConstants.EndPoints.HEALTH,
            method = RequestMethod.GET,
            beanClass = CodeHandler.class,
            beanMethod = "healthCheck",
            operation = @Operation(
                operationId = "healthCheck",
                summary = "Service health check",
                description = "Endpoint for service health monitoring",
                tags = {"health"},
                responses = {
                    @ApiResponse(responseCode = "200", description = "Service healthy",
                        content = @Content(schema = @Schema(implementation = HealthResponse.class))),
                    @ApiResponse(responseCode = "503", description = "Service unavailable",
                        content = @Content(schema = @Schema(implementation = HealthResponse.class)))
                }
            )
        )
    })
    public RouterFunction<ServerResponse> routeCode() {
        return RouterFunctions.route()
                .GET(AppConstants.EndPoints.GENERATE_CODE, codeHandler::generateCode)
                .POST(AppConstants.EndPoints.GENERATE_CODE_POST, codeHandler::generateCodePost)
                .GET(AppConstants.EndPoints.HEALTH, codeHandler::healthCheck)
                .build();
    }
}