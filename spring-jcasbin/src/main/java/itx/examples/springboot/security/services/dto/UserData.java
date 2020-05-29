package itx.examples.springboot.security.services.dto;

import java.util.HashSet;
import java.util.Set;

public class UserData {

    private final String userName;
    private final String password;

    public UserData(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

}
