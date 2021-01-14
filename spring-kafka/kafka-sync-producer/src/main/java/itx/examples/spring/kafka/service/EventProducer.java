package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.events.TxMessageAsyncEvent;

public interface EventProducer {

    void sendMessage(TxMessageAsyncEvent message) throws EventPublishException;

}
