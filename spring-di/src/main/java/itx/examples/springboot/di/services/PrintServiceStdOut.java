package itx.examples.springboot.di.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintServiceStdOut implements PrintService {

    private static final Logger LOG = LoggerFactory.getLogger(PrintServiceStdOut.class);

    @Override
    public String print(String message) {
        String result = "OUT: " + message;
        LOG.info(message);
        return result;
    }

}
