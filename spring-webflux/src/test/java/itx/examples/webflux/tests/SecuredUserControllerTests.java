package itx.examples.webflux.tests;

import itx.examples.webflux.dto.CreateUserData;
import itx.examples.webflux.dto.LoginRequest;
import itx.examples.webflux.dto.Token;
import itx.examples.webflux.dto.UserData;
import itx.examples.webflux.services.LoginService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecuredUserControllerTests {

    private static String firstUserId;
    private static Token token;

    @Autowired
    LoginService loginService;

    @Autowired
    private WebTestClient webClient;

    @Test
    @Order(0)
    @DisplayName("Login and get Access Token")
    void getAccessToken() {
        LoginRequest loginRequest = new LoginRequest("admin", "secret");
        EntityExchangeResult<Token> entityExchangeResult = webClient.post().uri("/secured/login")
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Token.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertNotNull(entityExchangeResult.getResponseBody().getToken());
        token = entityExchangeResult.getResponseBody();
        assertTrue(loginService.validate(token));
    }

    @Test
    @Order(1)
    @DisplayName("Should get no users")
    void getUsersEmpty() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/secured/users")
                .header("Authorization", token.getToken())
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
        EntityExchangeResult<UserData> entityExchangeResult = webClient.post().uri("/secured/users")
                .bodyValue(createUserData)
                .header("Authorization", token.getToken())
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
    @DisplayName("Get users")
    void getUsersAll() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/secured/users")
                .header("Authorization", token.getToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData[].class).returnResult();
        assertEquals(1, entityExchangeResult.getResponseBody().length);
    }

    @Test
    @Order(4)
    @DisplayName("Remove first user")
    void removeFirstUser() {
        EntityExchangeResult<UserData> entityExchangeResult = webClient.delete().uri("/secured/users/{id}", firstUserId)
                .header("Authorization", token.getToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserData.class).returnResult();
        assertNotNull(entityExchangeResult.getResponseBody());
        assertEquals(firstUserId, entityExchangeResult.getResponseBody().getId());
    }

    @Test
    @Order(5)
    @DisplayName("Logout")
    void logout() {
        EntityExchangeResult<Void> entityExchangeResult = webClient.post().uri("/secured/logout")
                .bodyValue(token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class).returnResult();
        assertFalse(loginService.validate(token));
    }

}
