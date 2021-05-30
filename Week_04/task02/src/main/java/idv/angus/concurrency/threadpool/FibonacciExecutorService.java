package idv.angus.concurrency.threadpool;

import idv.angus.concurrency.mission.FibonacciMissionCallable;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class FibonacciExecutorService {

    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();

        // CPU 核心數是有限的，因此若自己 new ，就沒辦法統一的管理、掌握
        // 線程，在系統內是佔用較多資源的重量級資源
        // 所以我們採用池化的方式
        // 這邊使用現成的 Executors 工具類幫我們創立線程池
        // 這邊就不去自己做 ThreadFactory 了，先用預設的
        final ExecutorService pool = Executors.newFixedThreadPool(1);

        // 利用線程池封裝 Future（未來）當回傳值的 submit 方法
        final Future<Integer> submit = pool.submit(new FibonacciMissionCallable());

        try {
            System.out.println("异步计算结果为：" + submit.get());
            System.out.println("使用时间：" + Instant.now().minusMillis(start).toEpochMilli() + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
