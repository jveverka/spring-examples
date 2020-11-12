package itx.examples.spring.kafka.producer.service;

import itx.examples.spring.kafka.events.AccountAsyncEvent;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;

public interface EventProducer {

    void sendMessage(DataMessageAsyncEvent message) throws EventPublishException;

    void sendAccountMessage(AccountAsyncEvent accountAsyncEvent) throws EventPublishException;

}
