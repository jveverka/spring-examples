package itx.examples.springboot.security.springsecurity.jwt.rest;

import itx.examples.springboot.security.springsecurity.jwt.rest.dto.LoginUserNamePasswordRequest;
import itx.examples.springboot.security.springsecurity.jwt.services.JWTUtils;
import itx.examples.springboot.security.springsecurity.jwt.services.UserAccessService;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.JWToken;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.LoginRequest;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.UserData;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/services/security/")
public class SecurityRestController {

    private final UserAccessService userAccessService;
    private final HttpServletRequest request;

    @Autowired
    public SecurityRestController(UserAccessService userAccessService, HttpServletRequest request) {
        this.userAccessService = userAccessService;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseEntity<UserData> login(@RequestBody LoginUserNamePasswordRequest loginRequest) {
        Optional<UserData> userData = userAccessService.login(new LoginRequest(UserId.from(loginRequest.getUserName()), loginRequest.getPassword()));
        if (userData.isPresent()) {
            return ResponseEntity.ok().body(userData.get());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        String authorization = request.getHeader("Authorization");
        JWToken jwToken = JWToken.from(JWTUtils.extractJwtToken(authorization));
        if (userAccessService.logout(jwToken)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}