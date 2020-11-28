package itx.examples.webflux.tests;

import itx.examples.webflux.dto.CreateUserData;
import itx.examples.webflux.dto.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecuredNegativeTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    void getUsersEmpty() {
        EntityExchangeResult<UserData[]> entityExchangeResult = webClient.get().uri("/secured/users")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(UserData[].class).returnResult();
    }

    @Test
    void createFirstUser() {
        CreateUserData createUserData = new CreateUserData("j@v.c", "juraj");
        EntityExchangeResult<UserData> entityExchangeResult = webClient.post().uri("/secured/users")
                .bodyValue(createUserData)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(UserData.class).returnResult();
    }

    @Test
    void removeFirstUser() {
        EntityExchangeResult<UserData> entityExchangeResult = webClient.delete().uri("/secured/users/{id}", "some-id")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(UserData.class).returnResult();
    }

}
