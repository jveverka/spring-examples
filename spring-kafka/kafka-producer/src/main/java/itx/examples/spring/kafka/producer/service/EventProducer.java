package itx.examples.spring.kafka.producer.service;

import itx.examples.spring.kafka.events.AccountAsyncEvent;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;

public interface EventProducer {

    void sendMessage(DataMessageAsyncEvent message);

    void sendAccountMessage(AccountAsyncEvent accountAsyncEvent);

}
