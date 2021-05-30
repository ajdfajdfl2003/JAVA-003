package idv.angus.concurrency.object;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class WaitAndNotifyThreadSleep {
    private static int result;

    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();

        final FibonacciMission mission = new FibonacciMission();
        final Thread calculator = new Thread(() -> {
            try {
                mission.sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Current thread: " + Thread.currentThread().getName());
        }, "WaitAndNotifyThreadSleep_calculator_1");

        final Thread student = new Thread(() -> {
            try {
                result = mission.getResult();
                System.out.println("异步计算结果为：" + result);
                System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "WaitAndNotifyThreadSleep_student_1");

        // student.start() 因為需要等 notify，所以要先寫
        // 而 calculator.start() 需要 經過計算 後才會有結果
        // 也就是 student 先等，然後 calculator 算好結果 notify student 來拿
        student.start();
        calculator.start();
        // 但在這個場景下套用 Wait and Notify
        // 且要不管 student.start 和 calculator.start() 誰先誰後的話，
        // 那麼 計算之前 一定要先等一下
        // 理由是我一定要確保 student 一定會收到 calculator 的 notify，
        // 若 student 還沒有開始 wait，而 calculator 先發出 notify 了，
        // 那麼 student 就永遠也收不到通知，而永無止境的等待下去了
    }

    static class FibonacciMission {
        private final Object lock = new Object();
        private int result;

        void sum() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("calculator sum");
            synchronized (lock) {
                result = fibo(36);
                lock.notifyAll();
                System.out.println("calculator notify student");
            }
        }

        int getResult() throws InterruptedException {
            System.out.println("student is waiting");
            synchronized (lock) {
                lock.wait();
            }
            System.out.println("student get the notification");
            return result;
        }

        private int fibo(int a) {
            if (a < 2)
                return 1;
            return fibo(a - 1) + fibo(a - 2);
        }
    }
}
