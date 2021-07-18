package idv.angus.redis.lock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RedisLockerTest {
    private final int threadAmount = 100;
    private final int loopNum = 1_000_000;

    @Test
    public void read_write() throws InterruptedException {
        final RedisLocker redisLocker = new RedisLocker("counter");
        final Counter counter = new Counter(redisLocker);
        final ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);
        IntStream.range(0, loopNum).parallel().forEach(i -> executorService.submit(counter::add));
        TimeUnit.SECONDS.sleep(10);
        System.out.println("AtomicCounter final answer: " + counter.getSum());
    }
}