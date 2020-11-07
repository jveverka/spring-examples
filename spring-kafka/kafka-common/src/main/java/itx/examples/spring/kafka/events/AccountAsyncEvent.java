package itx.examples.spring.kafka.events;

public interface AccountAsyncEvent extends AsyncEvent {

    String getId();

}
