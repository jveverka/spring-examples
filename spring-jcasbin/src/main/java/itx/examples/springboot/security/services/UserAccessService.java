package itx.examples.springboot.security.services;

import itx.examples.springboot.security.services.dto.LoginRequest;
import itx.examples.springboot.security.services.dto.UserData;

import java.util.Optional;

public interface UserAccessService {

    Optional<UserData> login(String sessionId, LoginRequest loginRequest);

    Optional<UserData> isAuthenticated(String sessionId);

    void logout(String sessionId);

}
