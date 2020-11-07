package itx.examples.spring.kafka.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventWrapper<T extends AsyncEvent> {

    private final T event;

    @JsonCreator
    public EventWrapper(@JsonProperty("event") T event) {
        this.event = event;
    }

    public T getEvent() {
        return event;
    }

    @JsonIgnore
    public Class<? extends AsyncEvent> getType() {
        return event.getClass();
    }

}
