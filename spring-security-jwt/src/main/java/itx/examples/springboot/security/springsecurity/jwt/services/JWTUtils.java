package itx.examples.springboot.security.springsecurity.jwt.services;

public final class JWTUtils {

    public static final String BEARER_PREFIX = "Bearer ";

    private JWTUtils() {
        throw new UnsupportedOperationException("Do not instantiate utility class.");
    }

    public static String createAuthorizationHeader(String jwToken) {
        return BEARER_PREFIX + jwToken;
    }

    public static String extractJwtToken(String authorization) {
        return authorization.substring(BEARER_PREFIX.length(), authorization.length()).trim();
    }

    public static String removeSignature(String jwToken) {
        return jwToken.substring(0, (jwToken.lastIndexOf('.') + 1));
    }

}
