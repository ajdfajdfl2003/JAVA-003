package idv.angus.concurrency.future;

import idv.angus.concurrency.mission.FibonacciMissionCallable;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class FibonacciFutureTaskWithPool {
    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();

        // Using FutureTask
        final FutureTask<Integer> future = new FutureTask<>(new FibonacciMissionCallable());
        // This time, we using thread pool
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(future);

        try {
            System.out.println("异步计算结果为：" + future.get());
            System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

}
