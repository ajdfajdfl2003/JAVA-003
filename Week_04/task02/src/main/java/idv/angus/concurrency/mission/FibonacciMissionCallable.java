package idv.angus.concurrency.mission;

import java.util.concurrent.Callable;

public class FibonacciMissionCallable implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println("Current thread: " + Thread.currentThread().getName());
        return sum();
    }

    private int sum() {
        return fibo(36);
    }

    private int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
