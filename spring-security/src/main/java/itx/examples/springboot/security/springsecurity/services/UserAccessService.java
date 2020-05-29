package itx.examples.springboot.security.springsecurity.services;

import itx.examples.springboot.security.springsecurity.services.dto.LoginRequest;
import itx.examples.springboot.security.springsecurity.services.dto.SessionId;
import itx.examples.springboot.security.springsecurity.services.dto.UserData;

import java.util.Optional;

public interface UserAccessService {

    Optional<UserData> login(SessionId sessionId, LoginRequest loginRequest);

    Optional<UserData> isAuthenticated(SessionId sessionId);

    void logout(SessionId sessionId);

}
