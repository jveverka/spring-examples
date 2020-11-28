package itx.examples.webflux.services;

import itx.examples.webflux.dto.LoginRequest;
import itx.examples.webflux.dto.Token;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<Token> login(LoginRequest loginRequest);

    boolean validate(Token token);

    Mono<Void> logout(Token token);

}
