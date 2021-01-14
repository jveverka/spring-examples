package itx.examples.spring.kafka.service;

import itx.examples.spring.kafka.dto.MessageReply;

import java.util.concurrent.CompletableFuture;

public interface EventConsumer {

    void setTransaction(String txId, CompletableFuture<MessageReply> completableFuture);

}
