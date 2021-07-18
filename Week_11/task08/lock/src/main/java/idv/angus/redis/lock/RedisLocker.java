package idv.angus.redis.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

public class RedisLocker {
    private static final long millisecondsToExpire = 3000L;
    private static final long timeoutMilliSeconds = millisecondsToExpire + 1000L;
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空閑連接數量，預設 8 個
        jedisPoolConfig.setMaxIdle(500);
        // 最大連接數，預設 8 個
        jedisPoolConfig.setMaxTotal(1000);
        // 最小空閑連接數量，預設 0 個
        jedisPoolConfig.setMinIdle(500);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        // 对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(jedisPoolConfig, "localhost");
    }

    private final String lockName;

    public RedisLocker(String lockName) {
        this.lockName = lockName;
    }

    public boolean lock() {
        try (Jedis jedis = jedisPool.getResource()) {
            final Instant startTime = Instant.now();
            for (; ; ) {
                String lock = jedis.set(lockName, "lock", SetParams.setParams().nx().px(millisecondsToExpire));
                if ("OK".equals(lock)) {
                    return true;
                }
                if (Duration.between(startTime, Instant.now()).toMillis() >= timeoutMilliSeconds) {
                    return false;
                }
            }
        }
    }

    public boolean unlock() {
        try (Jedis jedis = jedisPool.getResource()) {
            String script =
                    "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                            "   return redis.call('del',KEYS[1]) " +
                            "else" +
                            "   return 0 " +
                            "end";
            Object result = jedis.eval(script, Collections.singletonList(lockName),
                    Collections.singletonList("lock"));
            return "1".equals(result.toString());
        }
    }
}
