package itx.examples.springboot.security.springsecurity.rest;

import itx.examples.springboot.security.springsecurity.services.UserAccessService;
import itx.examples.springboot.security.springsecurity.services.dto.LoginRequest;
import itx.examples.springboot.security.springsecurity.services.dto.SessionId;
import itx.examples.springboot.security.springsecurity.services.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/services/security/")
public class SecurityRestController {

    private final UserAccessService userAccessService;
    private final HttpSession httpSession;

    @Autowired
    public SecurityRestController(UserAccessService userAccessService, HttpSession httpSession) {
        this.userAccessService = userAccessService;
        this.httpSession = httpSession;
    }

    @PostMapping("/login")
    public ResponseEntity<UserData> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserData> userData = userAccessService.login(SessionId.from(httpSession.getId()), loginRequest);
        if (userData.isPresent()) {
            return ResponseEntity.ok().body(userData.get());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        userAccessService.logout(SessionId.from(httpSession.getId()));
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }
}