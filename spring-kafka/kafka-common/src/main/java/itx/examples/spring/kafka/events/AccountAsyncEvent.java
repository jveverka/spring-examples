package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AccountAsyncEvent extends AsyncEvent {

    String getId();

    @JsonIgnore
    String keyMessageKey();

}
