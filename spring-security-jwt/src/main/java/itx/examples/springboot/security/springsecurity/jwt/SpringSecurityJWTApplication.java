package itx.examples.springboot.security.springsecurity.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityJWTApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJWTApplication.class, args);
    }

}
