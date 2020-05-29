package itx.examples.springboot.security.springsecurity.services;

import itx.examples.springboot.security.springsecurity.services.dto.LoginRequest;
import itx.examples.springboot.security.springsecurity.services.dto.Password;
import itx.examples.springboot.security.springsecurity.services.dto.SessionId;
import itx.examples.springboot.security.springsecurity.services.dto.UserData;
import itx.examples.springboot.security.springsecurity.services.dto.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserAccessServiceImpl implements UserAccessService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccessServiceImpl.class);

    private final Map<SessionId, UserData> sessions;
    private final Map<UserId, UserData> users;

    public UserAccessServiceImpl() {
        this.sessions = new ConcurrentHashMap<>();
        this.users = new HashMap<>();
        this.users.put(UserId.from("joe"), new UserData(UserId.from("joe"), Password.from("secret"), "ROLE_USER"));
        this.users.put(UserId.from("jane"), new UserData(UserId.from("jane"), Password.from("secret"), "ROLE_ADMIN", "ROLE_USER"));
        this.users.put(UserId.from("alice"), new UserData(UserId.from("alice"), Password.from("secret"), "ROLE_PUBLIC"));
    }

    @Override
    public Optional<UserData> login(SessionId sessionId, LoginRequest loginRequest) {
        UserData userData = users.get(UserId.from(loginRequest.getUserName()));
        if (userData != null && userData.verifyPassword(loginRequest.getPassword())) {
            LOG.info("login OK: {} {}", sessionId, loginRequest.getUserName());
            sessions.put(sessionId, userData);
            return Optional.of(userData);
        }
        LOG.info("login Failed: {} {}", sessionId, loginRequest.getUserName());
        return Optional.empty();
    }

    @Override
    public Optional<UserData> isAuthenticated(SessionId sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    @Override
    public void logout(SessionId sessionId) {
        LOG.info("logout: {}", sessionId);
        sessions.remove(sessionId);
    }

}
