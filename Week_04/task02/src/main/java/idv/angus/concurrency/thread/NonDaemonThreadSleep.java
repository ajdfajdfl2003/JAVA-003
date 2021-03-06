package idv.angus.concurrency.thread;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class NonDaemonThreadSleep {
    private static int result;

    public static void main(String[] args) throws InterruptedException {
        long start = Instant.now().toEpochMilli();

        // Using new Thread for this part
        final Thread mission = new Thread(() -> {
            result = sum();
            System.out.println("Current thread: " + Thread.currentThread().getName());
        });
        mission.setName("NonDaemonThreadSleep_1");
        // But this time we using non-daemon mode
        // Although daemon default value is false
        // This mean the main thread will wait sub-thread(mission: sum())
        mission.setDaemon(false);
        // Run this as new thread instead of main thread
        mission.start();

        // This is for wait for sub-thread(mission: sum())
        //  -> let result = sum() do first, and print result to stdout
        TimeUnit.SECONDS.sleep(1);
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
        // 缺點就是那個等待時間，會依照 sum 的邏輯操作時間而有所變動
        // 也就是說 sum 的時間若現在來到了 1 秒，則睡覺時間秒數也需要調整到兩秒之類的
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
