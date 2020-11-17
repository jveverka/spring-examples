package itx.examples.webflux.services;

import itx.examples.webflux.dto.UserData;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PublishTask {

    private static final Logger LOG = LoggerFactory.getLogger(PublishTask.class);

    private final List<UserData> users;
    private final int startIndex;
    private final int n;
    private final Subscriber<? super UserData> subscriber;

    private boolean stopped;

    public PublishTask(List<UserData> users, long startIndex, long n, Subscriber<? super UserData> subscriber) {
        this.users = users;
        this.startIndex = (int)startIndex;
        this.n = (int)n;
        this.subscriber = subscriber;
        this.stopped = false;
    }

    public void publish() {
        int si = stopIndex(users.size(), startIndex, n);
        LOG.info("Publishing started size={} fromIndex={} n={} stopIndex={}", users.size(), startIndex, n, si);
        int i = startIndex;
        if (startIndex < users.size()) {
            for (; i<si; i++) {
                if (stopped) {
                    LOG.info("interrupted {}", i);
                    return;
                }
                UserData userData = users.get(i);
                LOG.info("  Publishing {} {}:{}:{}", i, userData.getId(), userData.getEmail(), userData.getName());
                subscriber.onNext(userData);
                /*
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LOG.error("Error: {}", e.getMessage());
                }
                */
            }
            if (i == users.size()) {
                LOG.info("onComplete");
                subscriber.onComplete();
            }
        } else {
            LOG.info("onComplete");
            subscriber.onComplete();
        }
        LOG.info("Publishing done {}", i);
    }

    private int stopIndex(int size, int startIndex, int n) {
        if ((startIndex + n) >= size) {
            return size;
        } else {
            return (startIndex + n);
        }
    }

    public void setStopped() {
        LOG.info("setStopped");
        this.stopped = true;
    }

}
