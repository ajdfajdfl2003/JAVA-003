package idv.angus.redis.lock;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class RedisLockerTest {
    private static final int loopNum = 1_000;

    @Test
    public void read_write() throws InterruptedException {
        final RedisLocker redisLocker = new RedisLocker("counter");
        CountDownLatch countDownLatch = new CountDownLatch(loopNum);
        final Counter counter = new Counter(redisLocker, countDownLatch);
        final ExecutorService executorService = Executors.newFixedThreadPool(loopNum);
        IntStream.range(0, loopNum).forEach(i -> executorService.execute(counter::add));
        countDownLatch.await();
        System.out.println("AtomicCounter final answer: " + counter.getSum());
    }
}