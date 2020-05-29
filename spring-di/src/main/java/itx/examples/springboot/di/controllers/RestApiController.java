package itx.examples.springboot.di.controllers;

import itx.examples.springboot.di.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/service")
//@Scope("prototype") //<- uncomment this to change the scope !
public class RestApiController {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiController.class);

    private final ClientService clientService;
    private final HttpSession httpSession;
    private final HttpServletRequest requestInjected;

    public RestApiController(@Autowired ClientService clientService,
                             @Autowired HttpSession httpSession,
                             @Autowired HttpServletRequest requestInjected) {
        LOG.info("creating ...");
        this.clientService = clientService;
        this.httpSession = httpSession;
        this.requestInjected = requestInjected;
    }

    @PostConstruct
    private void init()  {
        LOG.info("init ...");
    }

    @GetMapping(path = "/print-default")
    public String printDefault(String message, HttpServletRequest request) {
        LOG.info("printDefault: {}, {}", httpSession.getId(), request.getRequestURI());
        return clientService.printDefault(message);
    }

    @GetMapping(path = "/print-err")
    public String printStdErr(String message, HttpServletRequest request) {
        LOG.info("printStdErr: {} {}", httpSession.getId(), request.getRequestURI());
        return clientService.printStdErr(message);
    }

    @GetMapping(path = "/print-out")
    public String printStdOut(String message, HttpServletRequest request) {
        LOG.info("printStdOut: {} {}", httpSession.getId(), request.getRequestURI());
        return clientService.printStdOut(message);
    }

    @GetMapping(path = "/data")
    public String getData(HttpServletRequest request) {
        LOG.info("getData: {} {} {}", httpSession.getId(), request.getRequestURI(), requestInjected.getRequestURI());
        return clientService.getData();
    }

    @GetMapping(path = "/data-prototype")
    public String getDataPrototype(HttpServletRequest request) {
        LOG.info("getDataPrototype: {} {} {}", httpSession.getId(), request.getRequestURI(), requestInjected.getRequestURI());
        return clientService.getDataPrototype();
    }

    @PreDestroy
    private void shutdown()  {
        LOG.info("##destroy.");
    }

}
