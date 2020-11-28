package itx.examples.webflux.services;

import itx.examples.webflux.dto.LoginRequest;
import itx.examples.webflux.dto.Token;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginServiceImpl implements LoginService {

    private final Map<String, String> users;
    private final Set<Token> tokens;

    public LoginServiceImpl() {
        this.users = new ConcurrentHashMap<>();
        this.tokens = new HashSet<>();
        this.users.put("admin", "secret");
    }

    @Override
    public Mono<Token> login(LoginRequest loginRequest) {
        String password = users.get(loginRequest.getUsername());
        if (password != null && password.equals(loginRequest.getPassword())) {
            Token token = new Token(UUID.randomUUID().toString());
            this.tokens.add(token);
            return Mono.just(token);
        }
        return Mono.error(new RuntimeException());
    }

    @Override
    public boolean validate(Token token) {
        return this.tokens.contains(token);
    }

    @Override
    public Mono<Void> logout(Token token) {
        this.tokens.remove(token);
        return Mono.empty();
    }

}
