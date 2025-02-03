package com.franmunozbetanzos.portfolio.constant;

public class ApiConstants {

    // Constantes de la API
    public static final String API_BASE_PATH = "/api/v1";
    public static final String AUTH_PATH = API_BASE_PATH + "/auth";
    public static final String PUBLIC_PATH = API_BASE_PATH + "/public";
    public static final String TEST_PATH = API_BASE_PATH + "/test";

    // Constantes AuthController
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";

    // Constantes TestController
    public static final String TEST_ERROR_PATH = "/test-error";
    public static final String ALL_PATH = "/**";

    // Constantes de seguridad
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_HEADER_STRING = "Authorization";

    // Constantes de roles
    public static final String ROLE_ = "ROLE_";
    public static final String ROLE_USER = ROLE_ + "USER";
    public static final String ROLE_ADMIN = ROLE_ + "ADMIN";

    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }


}