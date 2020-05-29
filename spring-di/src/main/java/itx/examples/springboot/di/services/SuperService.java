package itx.examples.springboot.di.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperService implements PrintService, DataService {

    private static final Logger LOG = LoggerFactory.getLogger(SuperService.class);

    @Override
    public String getData() {
        return "SUPER";
    }

    @Override
    public String print(String message) {
        String result = "SUPER: " + message;
        LOG.warn(result);
        return result;
    }

}
