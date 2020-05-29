package itx.examples.springboot.di.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintServiceStdErr implements PrintService {

    private static final Logger LOG = LoggerFactory.getLogger(PrintServiceStdErr.class);

    @Override
    public String print(String message) {
        String result = "ERR: " + message;
        LOG.error(result);
        return result;
    }

}
