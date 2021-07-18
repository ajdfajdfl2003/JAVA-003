package idv.angus.redis;

import idv.angus.redis.lock.Redis;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CounterTest {
    private static final int loopNum = 100_000;

    @Test
    public void read_write() throws InterruptedException {
        final Redis redis = new Redis("counter");
        CountDownLatch countDownLatch = new CountDownLatch(loopNum + loopNum / 2);
        final Counter counter = new Counter(redis, countDownLatch);
        final ExecutorService executorService = Executors.newFixedThreadPool(2000);
        IntStream.range(0, loopNum).forEach(i -> executorService.execute(counter::add));
        IntStream.range(0, loopNum / 2).forEach(i -> executorService.execute(counter::minus));
        countDownLatch.await();
        System.out.println("AtomicCounter final answer: " + counter.getSum());
    }
}