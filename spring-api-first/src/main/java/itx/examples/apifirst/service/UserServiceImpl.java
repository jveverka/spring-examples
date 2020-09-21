package itx.examples.apifirst.service;

import itx.examples.apifirst.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Map<String, User> users;

    public UserServiceImpl() {
        this.users = new HashMap<>();
    }

    @Override
    public List<User> getUsers() {
        return this.users.values().stream().collect(Collectors.toList());
    }

    @Override
    public User createUser(String name, String email, Boolean enabled) {
        String id = UUID.randomUUID().toString();
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setEnabled(enabled);
        this.users.put(id, user);
        return user;
    }

    @Override
    public void delete(String id) {
        this.users.remove(id);
    }

}
