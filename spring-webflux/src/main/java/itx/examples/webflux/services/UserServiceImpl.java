package itx.examples.webflux.services;

import itx.examples.webflux.dto.UserData;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Mono<UserData> getEmployee(String id) {
        return null;
    }

    @Override
    public Flux<UserData> getAll() {
        return null;
    }

    @Override
    public Mono<UserData> update(UserData employee) {
        return null;
    }

    @Override
    public Mono<UserData> delete(String id) {
        return null;
    }

}
