package itx.examples.spring.kafka.service;

public class EventPublishException extends RuntimeException {

    public EventPublishException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
