package itx.examples.spring.kafka.consumer.service;

import itx.examples.spring.kafka.events.CreateAccountAsyncEvent;
import itx.examples.spring.kafka.events.DataMessageAsyncEvent;
import itx.examples.spring.kafka.events.AsyncEvent;
import itx.examples.spring.kafka.events.DeleteAccountAsyncEvent;
import itx.examples.spring.kafka.events.EventWrapper;
import itx.examples.spring.kafka.events.TransferFundsAsyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    private final Map<String, DataMessageAsyncEvent> messages;
    private final AccountService accountService;

    @Autowired
    public EventConsumer(AccountService accountService) {
        this.accountService = accountService;
        this.messages = new ConcurrentHashMap<>();
    }

    public Collection<DataMessageAsyncEvent> getMessages() {
        return Collections.unmodifiableCollection(this.messages.values());
    }

    public void resetMessages() {
        this.messages.clear();
    }

    @KafkaListener(topics = "request-test-topic", groupId = "the-group")
    public void consume(EventWrapper<? extends AsyncEvent> message) throws IOException {
        LOGGER.info("#### Consumed message -> {} {}", message.getType());
        if (message.getType().equals(DataMessageAsyncEvent.class)) {
            EventWrapper<DataMessageAsyncEvent> wrapper = (EventWrapper<DataMessageAsyncEvent>)message;
            messages.put(wrapper.getEvent().getId(), wrapper.getEvent());
        } else if (message.getType().equals(CreateAccountAsyncEvent.class)) {
            EventWrapper<CreateAccountAsyncEvent> wrapper = (EventWrapper<CreateAccountAsyncEvent>)message;
            accountService.createAccount(wrapper.getEvent());
        } else if (message.getType().equals(DeleteAccountAsyncEvent.class)) {
            EventWrapper<DeleteAccountAsyncEvent> wrapper = (EventWrapper<DeleteAccountAsyncEvent>)message;
            accountService.deleteAccount(wrapper.getEvent());
        } else if (message.getType().equals(TransferFundsAsyncEvent.class)) {
            EventWrapper<TransferFundsAsyncEvent> wrapper = (EventWrapper<TransferFundsAsyncEvent>)message;
            accountService.transferFunds(wrapper.getEvent());
        }
    }

}
