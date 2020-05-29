package itx.examples.springboot.security.springsecurity.services.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class UserData {

    private final UserId userId;
    private final Password password;
    private final Set<RoleId> roles;

    @JsonCreator
    public UserData(@JsonProperty("userId") UserId userId,
                    @JsonProperty("password") Password password,
                    @JsonProperty("roles") Set<RoleId> roles) {
        this.userId = userId;
        this.password = password;
        this.roles = roles;
    }

    public UserData(UserId userId, Password password, String ... roles) {
        this.userId = userId;
        this.password = password;
        this.roles = new HashSet<>();
        for (String role: roles) {
            this.roles.add(new RoleId(role));
        }
    }

    public UserId getUserId() {
        return userId;
    }

    public boolean verifyPassword(String password) {
        return this.password.verify(password);
    }

    public Set<RoleId> getRoles() {
        return roles;
    }

}
