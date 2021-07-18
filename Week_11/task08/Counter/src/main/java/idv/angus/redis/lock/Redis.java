package idv.angus.redis.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Redis {
    private static final Config config;
    private static final RedissonClient client;

    static {
        config = new Config();
        config.setThreads(20);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        client = Redisson.create(config);
    }

    private final String lockName;

    public Redis(String name) {
        this.lockName = name;
        client.getAtomicLong(lockName).set(0L);
    }

    public void increment() {
        client.getAtomicLong(lockName).incrementAndGet();
    }

    public long getTotal() {
        return client.getAtomicLong(lockName).get();
    }

    public void decrement() {
        client.getAtomicLong(lockName).decrementAndGet();
    }
}
