package itx.examples.spring.kafka.dto;

import java.util.concurrent.CompletableFuture;

public class TxContext {

    private final Long started;
    private final CompletableFuture<MessageReply> completableFuture;

    public TxContext(Long started, CompletableFuture<MessageReply> completableFuture) {
        this.started = started;
        this.completableFuture = completableFuture;
    }

    public Long getStarted() {
        return started;
    }

    public CompletableFuture<MessageReply> getCompletableFuture() {
        return completableFuture;
    }

}
