package itx.examples.springboot.security.springsecurity.jwt.services.dto;

public class LoginRequest {

    private final UserId userId;
    private final String password;

    public LoginRequest(UserId userId,
                        String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

}
