package idv.angus.using_shardingsphere_proxy.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@ToString
public class Order {
    private final long buyerId;
    private final long payment;
    private final String status;
    private final long orderTime;
    private long oderId;

    public Order(long payment, String status, long orderTime) {
        this.buyerId = ThreadLocalRandom.current().nextLong(1_000_000);
        this.payment = payment;
        this.status = status;
        this.orderTime = orderTime;
    }
}
