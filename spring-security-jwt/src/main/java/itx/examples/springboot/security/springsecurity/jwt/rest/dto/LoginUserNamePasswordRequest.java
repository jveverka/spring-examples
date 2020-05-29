package itx.examples.springboot.security.springsecurity.jwt.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserNamePasswordRequest {

    private final String userName;
    private final String password;

    @JsonCreator
    public LoginUserNamePasswordRequest(@JsonProperty("userName") String userName,
                                        @JsonProperty("password")  String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
