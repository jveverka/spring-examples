package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.events.DataMessageAsyncEvent;

public interface EventProducer {

    void sendMessage(DataMessageAsyncEvent message) throws EventPublishException;

}
