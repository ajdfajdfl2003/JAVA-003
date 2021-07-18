package idv.angus.redis.lock;

import java.util.concurrent.CountDownLatch;

public class Counter {
    private final RedisLocker lock;
    private final CountDownLatch countDownLatch;
    private int sum = 0;

    public Counter(RedisLocker lock, CountDownLatch countDownLatch) {
        this.lock = lock;
        this.countDownLatch = countDownLatch;
    }

    public int getSum() {
        return sum;
    }

    public void add() {
        try {
            if (lock.lock()) {
                ++sum;
            } else {
                System.out.println("sum failed !!!");
            }
        } finally {
            if (!lock.unlock()) {
                System.out.println("unlock failed !!!");
            }
            countDownLatch.countDown();
        }
    }
}
