package itx.examples.webflux.services;

import itx.examples.webflux.dto.CreateUserData;
import itx.examples.webflux.dto.UserData;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final Map<String, UserData> data;

    public UserServiceImpl() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<UserData> create(CreateUserData createUserData) {
        UserData userData = new UserData(UUID.randomUUID().toString(), createUserData.getEmail(), createUserData.getName());
        data.put(userData.getId(), userData);
        return Mono.just(userData);
    }

    @Override
    public Mono<UserData> getEmployee(String id) {
        UserData userData = data.get(id);
        if (userData != null) {
            return Mono.just(userData);
        } else {
            return Mono.empty();
        }
    }

    @Override
    public Flux<UserData> getAll() {
        return Flux.fromIterable(data.values());
    }

    @Override
    public Mono<UserData> update(UserData employee) {
        data.put(employee.getId(), employee);
        return Mono.just(employee);
    }

    @Override
    public Mono<UserData> delete(String id) {
        UserData userData = data.remove(id);
        if (userData != null) {
            return Mono.just(userData);
        } else {
            return Mono.empty();
        }
    }

    @Override
    public void createUsersBulk(Integer n) {
        LOG.info("createUsersBulk n={} ...", n);
        for (int i=0; i<n; i++) {
            String id = "user-id-" + i;
            data.put(id, new UserData(id, "email" + i + "@corp.com", "name-" + i));
        }
        LOG.info("created {} users", n);
    }

    @Override
    public Publisher<UserData> getAllStream() {
        LOG.info("getAllStream: ");
        return new UserDataPublisher(data.values().stream().collect(Collectors.toUnmodifiableList()));
    }

}
