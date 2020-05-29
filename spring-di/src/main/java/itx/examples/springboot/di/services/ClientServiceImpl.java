package itx.examples.springboot.di.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final PrintService printService;
    private final PrintService printServiceErr;
    private final PrintService printServiceOut;
    private final DataService dataService;
    private final DataService dataServicePrototype;

    public ClientServiceImpl(@Autowired PrintService printService,
                             @Autowired @Qualifier("stdErr") PrintService printServiceErr,
                             @Autowired @Qualifier("stdOut") PrintService printServiceOut,
                             @Autowired DataService dataService,
                             @Autowired @Qualifier("prototype") DataService dataServicePrototype) {
        this.printService = printService;
        this.printServiceErr = printServiceErr;
        this.printServiceOut = printServiceOut;
        this.dataService = dataService;
        this.dataServicePrototype = dataServicePrototype;
    }


    @PostConstruct
    public void init() {
        LOG.info("init ...");
        printService.print("init ...");
        printServiceErr.print("init ....");
        printService.print(dataService.getData());
        printServiceOut.print("init .....");
    }

    @Override
    public String printDefault(String message) {
        return printService.print(message);
    }

    @Override
    public String printStdErr(String message) {
        return printServiceErr.print(message);
    }

    @Override
    public String printStdOut(String message) {
        return printServiceOut.print(message);
    }

    @Override
    public String getData() {
        return dataService.getData();
    }

    @Override
    public String getDataPrototype() {
        return dataServicePrototype.getData();
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("##destroy.");
    }

}
