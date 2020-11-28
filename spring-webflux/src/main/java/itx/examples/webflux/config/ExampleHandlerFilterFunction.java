package itx.examples.webflux.config;

import itx.examples.webflux.dto.Token;
import itx.examples.webflux.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class ExampleHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleHandlerFilterFunction.class);

    private final LoginService loginService;

    public ExampleHandlerFilterFunction(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) {
        Optional<String> authorization = serverRequest.headers().header("Authorization").stream().findFirst();
        if (authorization.isPresent() && loginService.validate(new Token(authorization.get()))) {
            LOG.info("filter: OK authorization={}", authorization.get());
            return handlerFunction.handle(serverRequest);
        } else {
            LOG.info("filter: UNAUTHORIZED");
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
