package itx.examples.webflux.services;

import itx.examples.webflux.dto.UserData;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDataPublisher implements Publisher<UserData>, Subscription {

    private static final Logger LOG = LoggerFactory.getLogger(UserDataPublisher.class);

    private final List<UserData> users;

    private Subscriber<? super UserData> subscriber;
    private long start;
    private PublishTask publishTask;

    public UserDataPublisher(List<UserData> users) {
        this.users = users;
    }

    @Override
    public void subscribe(Subscriber<? super UserData> subscriber) {
        LOG.info("subscribe");
        this.subscriber = subscriber;
        this.start = 0;
        this.subscriber.onSubscribe(this);
    }

    @Override
    public synchronized void request(long n) {
        long startIndex = start;
        LOG.info("request n={} start={}", n, startIndex);
        start = start + n;
        publishTask = new PublishTask(users, startIndex, n, subscriber);
        publishTask.publish();
    }

    @Override
    public void cancel() {
        LOG.info("cancel");
        if (publishTask != null) {
            publishTask.setStopped();
        }
    }

}
