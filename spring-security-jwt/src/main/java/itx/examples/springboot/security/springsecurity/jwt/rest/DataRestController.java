package itx.examples.springboot.security.springsecurity.jwt.rest;

import itx.examples.springboot.security.springsecurity.jwt.services.DataService;
import itx.examples.springboot.security.springsecurity.jwt.services.dto.ServerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/services/data")
public class DataRestController {

    private static final Logger LOG = LoggerFactory.getLogger(DataRestController.class);

    private final DataService dataService;

    @Autowired
    public DataRestController(DataService dataService) {
        this.dataService = dataService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/users/all")
    public ResponseEntity<ServerData> getDataForUsers(Authentication authentication) {
        LOG.info("getData: authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a ->
            LOG.info("  authority={}", a.getAuthority())
        );
        return ResponseEntity.ok().body(dataService.getSecuredData("Secured for USER/ADMIN " + authentication.getName()));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admins/all")
    public ResponseEntity<ServerData> getDataForAdmins(Authentication authentication) {
        LOG.info("getData: authentication={}", authentication.getName());
        authentication.getAuthorities().forEach(a ->
            LOG.info("  authority={}", a.getAuthority())
        );
        return ResponseEntity.ok().body(dataService.getSecuredData("Secured for ADMIN " + authentication.getName()));
    }

    @GetMapping("/users/super-admins/all")
    @PreAuthorize("hasAuthority('SUPER_ADMIN_AUTHORITY') and hasRole('SUPER_ADMIN') and hasPermission(filterObject, 'WRITE')")
    public ResponseEntity<ServerData> getDataForAdminsOnly(Authentication authentication) {
        return getDataForAdmins(authentication);
    }

}
