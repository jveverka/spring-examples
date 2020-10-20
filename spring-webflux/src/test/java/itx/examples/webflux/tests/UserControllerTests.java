package itx.examples.webflux.tests;

import itx.examples.webflux.dto.CreateUserData;
import itx.examples.webflux.dto.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {

    private static String firstUserId;
    private static String secondUserId;

    @Autowired
    private WebTestClient webClient;

    @Test
    @Order(1)
    @DisplayName("Should get no users")
    void getUsersEmpty() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData[].class).returnResult();
        assertEquals(0, entityExchangeResult.getResponseBody().length);
    }

    @Test
    @Order(2)
    @DisplayName("Create First User")
    void createFirstUser() {
        CreateUserData createUserData = new CreateUserData("j@v.c", "juraj");
        EntityExchangeResult<UserData> entityExchangeResult = webClient.post().uri("/users")
                .bodyValue(createUserData)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertNotNull(entityExchangeResult.getResponseBody().getId());
        assertEquals(createUserData.getEmail(), entityExchangeResult.getResponseBody().getEmail());
        assertEquals(createUserData.getName(), entityExchangeResult.getResponseBody().getName());
        firstUserId = entityExchangeResult.getResponseBody().getId();
    }

    @Test
    @Order(3)
    @DisplayName("Create Second User")
    void createSecondUser() {
        CreateUserData createUserData = new CreateUserData("a@b.c", "veverka");
        EntityExchangeResult<UserData> entityExchangeResult = webClient.post().uri("/users")
                .bodyValue(createUserData)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertNotNull(entityExchangeResult.getResponseBody().getId());
        assertEquals(createUserData.getEmail(), entityExchangeResult.getResponseBody().getEmail());
        assertEquals(createUserData.getName(), entityExchangeResult.getResponseBody().getName());
        secondUserId = entityExchangeResult.getResponseBody().getId();
    }

    @Test
    @Order(4)
    @DisplayName("Get users")
    void getUsersAll() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData[].class).returnResult();
        assertEquals(2, entityExchangeResult.getResponseBody().length);
    }

    @Test
    @Order(5)
    @DisplayName("Remove first user")
    void removeFirstUser() {
        EntityExchangeResult<UserData> entityExchangeResult = webClient.delete().uri("/users/{id}", firstUserId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertEquals(firstUserId, entityExchangeResult.getResponseBody().getId());
    }

    @Test
    @Order(6)
    @DisplayName("Remove second user")
    void removeSecondUser() {
        EntityExchangeResult<UserData> entityExchangeResult = webClient.delete().uri("/users/{id}", secondUserId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertEquals(secondUserId, entityExchangeResult.getResponseBody().getId());
    }

    @Test
    @Order(7)
    @DisplayName("Should get no users")
    void checkUsersEmpty() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData[].class).returnResult();
        assertEquals(0, entityExchangeResult.getResponseBody().length);
    }

}
