package itx.examples.springdata.test;

import itx.examples.springdata.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("h2")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void testUserService() {
        assertEquals(0, userService.getAll().size());
        String id = userService.createUser("john", "doe");
        assertNotNull(id);
        assertEquals(1, userService.getAll().size());
        userService.deleteUser(id);
        assertEquals(0, userService.getAll().size());
    }

}
