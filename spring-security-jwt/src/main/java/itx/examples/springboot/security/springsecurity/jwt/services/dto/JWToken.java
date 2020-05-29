package itx.examples.springboot.security.springsecurity.jwt.services.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JWToken {

    private final String token;

    @JsonCreator
    public JWToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static JWToken from(String token) {
        return new JWToken(token);
    }

}
