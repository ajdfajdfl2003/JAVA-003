package idv.angus.redis.lock;

public class Counter {
    private final RedisLocker lock;
    private int sum = 0;

    public Counter(RedisLocker lock) {
        this.lock = lock;
    }

    public int getSum() {
        return sum;
    }

    public void add() {
        try {
            if (lock.lock()) {
                ++sum;
            } else {
                System.out.println("sum failed !!!");
            }
        } finally {
            if (!lock.unlock()) {
                System.out.println("unlock failed !!!");
            }
        }
    }
}
