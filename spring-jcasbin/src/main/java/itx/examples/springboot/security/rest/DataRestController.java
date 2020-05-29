package itx.examples.springboot.security.rest;

import itx.examples.springboot.security.services.DataService;
import itx.examples.springboot.security.services.dto.ServerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/data")
public class DataRestController {

    private static final Logger LOG = LoggerFactory.getLogger(DataRestController.class);

    @Autowired
    private DataService dataService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users/all")
    public ResponseEntity<ServerData> getForUsersData(Authentication authentication) {
        LOG.info("getData: authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a->{
            LOG.info("  authority={}", a.getAuthority());
        });
        return ResponseEntity.ok().body(dataService.getSecuredData("Secured for USER/ADMIN " + authentication.getName()));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admins/all")
    public ResponseEntity<ServerData> getDataForAdmins(Authentication authentication) {
        LOG.info("getData: authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a->{
            LOG.info("  authority={}", a.getAuthority());
        });
        return ResponseEntity.ok().body(dataService.getSecuredData("Secured for ADMIN " + authentication.getName()));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/admins/state/{state}")
    public ResponseEntity<ServerData> setStateForAdmins(Authentication authentication, @PathVariable String state) {
        LOG.info("setState: authentication={} state={}", authentication.getName(), state);
        authentication.getAuthorities().forEach(a->{
            LOG.info("  authority={}", a.getAuthority());
        });
        dataService.setState(state);
        return ResponseEntity.ok().build();
    }

}
