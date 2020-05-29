package itx.examples.springbank.server.controller;

import itx.examples.springbank.common.dto.Client;
import itx.examples.springbank.common.dto.CreateClientRequest;
import itx.examples.springbank.common.dto.ClientId;
import itx.examples.springbank.common.dto.ServiceException;
import itx.examples.springbank.common.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/services/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/clients")
    public Collection<Client> getClients() throws ServiceException {
        return adminService.getClients();
    }

    @PostMapping("/client")
    public ClientId createClient(@RequestBody CreateClientRequest createClientRequest) throws ServiceException {
        return adminService.createClient(createClientRequest);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable(value="id") String id) throws ServiceException {
        adminService.deleteClient(new ClientId(id));
    }

}
