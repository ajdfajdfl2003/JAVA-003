package idv.angus.concurrency.future.completefuture;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class SupplyAsyncListenable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = Instant.now().toEpochMilli();

        final ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.supplyAsync(SupplyAsyncListenable::sum, executor)
                .whenComplete((result, throwable) -> {
                    if (!Objects.isNull(throwable)) return;
                    System.out.println("Current thread: " + Thread.currentThread().getName() + " do its job");
                    System.out.println("异步计算结果为：" + result);
                    System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
                });
        executor.shutdown();
        System.out.println("Current thread: " + Thread.currentThread().getName() + " thread done");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
