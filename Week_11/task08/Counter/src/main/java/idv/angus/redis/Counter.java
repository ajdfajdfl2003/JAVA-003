package idv.angus.redis;

import idv.angus.redis.lock.Redis;

import java.util.concurrent.CountDownLatch;

public class Counter {
    private final Redis redis;
    private final CountDownLatch countDownLatch;

    public Counter(Redis redis, CountDownLatch countDownLatch) {
        this.redis = redis;
        this.countDownLatch = countDownLatch;
    }

    public long getSum() {
        return redis.getTotal();
    }

    public void add() {
        try {
            redis.increment();
        } finally {
            countDownLatch.countDown();
        }
    }

    public void minus() {
        try {
            redis.decrement();
        } finally {
            countDownLatch.countDown();
        }
    }
}
