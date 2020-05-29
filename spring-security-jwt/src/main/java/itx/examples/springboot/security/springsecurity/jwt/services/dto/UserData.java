package itx.examples.springboot.security.springsecurity.jwt.services.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class UserData {

    private final UserId userId;
    private final Password password;
    private final Set<RoleId> roles;
    private final JWToken jwToken;

    @JsonCreator
    public UserData(@JsonProperty("userId") UserId userId,
                    @JsonProperty("jwToken") JWToken jwToken,
                    @JsonProperty("roles") Set<RoleId> roles) {
        this.userId = userId;
        this.password = null;
        this.roles = roles;
        this.jwToken = jwToken;
    }

    public UserData(UserId userId, Password password, JWToken jwToken, Set<RoleId> roles) {
        this.userId = userId;
        this.password = password;
        this.roles = roles;
        this.jwToken = jwToken;
    }

    public UserData(UserId userId, Password password, Set<RoleId> roles) {
        this.userId = userId;
        this.password = password;
        this.roles = roles;
        this.jwToken = null;
    }

    public UserData(UserId userId, Password password, String ... roles) {
        this.userId = userId;
        this.password = password;
        this.roles = new HashSet<>();
        for (String role: roles) {
            this.roles.add(new RoleId(role));
        }
        this.jwToken = null;
    }

    public UserId getUserId() {
        return userId;
    }

    public Set<RoleId> getRoles() {
        return roles;
    }

    public JWToken getJwToken() {
        return jwToken;
    }

    public boolean verifyPassword(String password) {
        if (this.password == null) {
            return false;
        }
        return this.password.verify(password);
    }

    public static UserData from(UserData userData, JWToken jwToken) {
        return new UserData(userData.userId, userData.password, jwToken, userData.roles);
    }

    public static UserData cloneAndRemoveJwToken(UserData userData) {
        return new UserData(userData.userId, userData.password, userData.roles);
    }

}
