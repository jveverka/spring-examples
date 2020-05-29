package itx.examples.springboot.di.config;

import itx.examples.springboot.di.services.DataService;
import itx.examples.springboot.di.services.PrintService;
import itx.examples.springboot.di.services.PrintServiceStdErr;
import itx.examples.springboot.di.services.SuperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    private final SuperService superService;
    private final PrintService printServiceStdErr;

    public AppConfig() {
        LOG.info("creating ...");
        this.superService = new SuperService();
        this.printServiceStdErr = new PrintServiceStdErr();
    }

    @PostConstruct
    public void init() {
        LOG.info("init ...");
    }

    @Bean(name="stdOut")
    @Primary
    public PrintService createOutPrintService() {
        LOG.info("creating PrintService stdOut");
        return superService;
    }

    @Bean(name="stdErr")
    public PrintService createErrPrintService() {
        LOG.info("creating PrintService stdErr");
        return printServiceStdErr;
    }

    @Bean
    public DataService createSuperService() {
        LOG.info("creating DataService");
        return superService;
    }

    @Bean(name="prototype")
    @Scope("prototype")
    public DataService createSuperServicePrototype() {
        LOG.info("creating DataService prototype");
        return new SuperService();
    }

    @PreDestroy
    public void shutdown() {
        LOG.info("##destroy.");
    }

}
