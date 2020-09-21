package itx.examples.apifirst.component;

import itx.examples.apifirst.api.ServicesApiDelegate;
import itx.examples.apifirst.model.CreateUser;
import itx.examples.apifirst.model.User;
import itx.examples.apifirst.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesComponent implements ServicesApiDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(ServicesComponent.class);

    private final UserService userService;

    public ServicesComponent(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> servicesUsersGet() {
        LOG.info("get users");
        return ResponseEntity.ok(userService.getUsers());
    }

    @Override
    public ResponseEntity<User> servicesUserPost(CreateUser createUser) {
        LOG.info("create user");
        return ResponseEntity.ok(userService.createUser(createUser.getName(), createUser.getEmail(), createUser.getEnabled()));
    }

    @Override
    public ResponseEntity<Void> servicesUsersUserIdDelete(String userId) {
        LOG.info("delete user");
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

}
