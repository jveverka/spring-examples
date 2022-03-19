package itx.examples.springboot.demo.logs;

import org.slf4j.Logger;
import org.slf4j.MDC;

import static java.util.Objects.nonNull;

public final class LogUtils {

    public static final String EXEC_TIME_KEY = "exec_time";

    public static void updateExecutionTime(Long execTime) {
        MDC.remove(EXEC_TIME_KEY);
        MDC.put(EXEC_TIME_KEY, nonNull(execTime) ? execTime.toString() : "NA" );
    }

    public static void clearExecutionTime() {
        MDC.remove(EXEC_TIME_KEY);
    }

    public static void clearAll() {
        MDC.clear();
    }

    public static void logHttpTraffic(Logger logger, long startTime, String message, Object... arguments) {
        long execTime = (System.nanoTime() - startTime)/1_000_000;
        updateExecutionTime(execTime);
        logger.info(message, arguments);
        clearExecutionTime();
    }

}
