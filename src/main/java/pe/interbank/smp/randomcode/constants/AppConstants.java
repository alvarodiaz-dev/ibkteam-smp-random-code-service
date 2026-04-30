package pe.interbank.smp.randomcode.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String SERVICE_NAME = "random-code-service";
    public static final String SERVICE_VERSION = "1.0.0";

    public static class EndPoints {
        public static final String GENERATE_CODE = "/api/v1/codes";
        public static final String GENERATE_CODE_POST = "/api/v1/codes/generate";
        public static final String HEALTH = "/health";
    }

    public static class Headers {
        public static final String X_CORRELATION_ID = "X-Correlation-Id";
        public static final String AUTHORIZATION = "Authorization";
        public static final String X_CLIENT_ID = "X-IBM-Client-Id";
        public static final String X_CLIENT_SECRET = "X-IBM-Client-Secret";
        public static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";
        public static final String SESSION_ID = "SESSION_ID";
        public static final String DEVICE_ID = "DEVICE_ID";
        public static final String COMPANY = "Interbank";
        public static final String STORE_CODE = "888";
    }

    public static class ErrorMessage {
        public static final String GENERIC_ERROR_CODE = "RANDOMCODE_E500_001";
        public static final String GENERIC_ERROR_MESSAGE = "Error processing request";
        public static final String VALIDATION_ERROR_CODE = "RANDOMCODE_E400_001";
        public static final String VALIDATION_ERROR_MESSAGE = "Invalid input data";
        public static final String CODE_GENERATION_ERROR = "Failed to generate code";
    }

    public static class Mocks {
        public static final String MOCK = "mock";
    }

    public static class CodeGeneration {
        public static final int MIN_CODE = 1000;
        public static final int MAX_CODE = 9999;
        public static final int CODE_LENGTH = 4;
    }

    public static class HttpStatus {
        public static final int OK = 200;
        public static final int CREATED = 201;
        public static final int BAD_REQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int SERVICE_UNAVAILABLE = 503;
    }
}