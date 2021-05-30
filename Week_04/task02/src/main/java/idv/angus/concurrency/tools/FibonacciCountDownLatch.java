package idv.angus.concurrency.tools;

import java.time.Instant;
import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class FibonacciCountDownLatch {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = Instant.now().toEpochMilli();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Integer> future = executor.submit(() -> sum(countDownLatch));

        countDownLatch.await();

        System.out.println("异步计算结果为：" + future.get());
        System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");

        executor.shutdown();
    }

    private static int sum(CountDownLatch countDownLatch) {
        try {
            return fibo(36);
        } finally {
            countDownLatch.countDown();
        }
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
