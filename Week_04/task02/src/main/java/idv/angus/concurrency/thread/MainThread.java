package idv.angus.concurrency.thread;

import java.time.Instant;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class MainThread {
    private static int result;

    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();

        // Using new Thread for this part
        final Thread mission = new Thread(() -> {
            result = sum();
            System.out.println("Current thread: " + Thread.currentThread().getName());
        });
        mission.setName("MainThread_1");
        // And run this mission in Main Thread
        mission.run();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
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
