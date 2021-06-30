package idv.angus.shardingspherespringbootstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class Order {
    private long buyerId;
    private long payment;
    private String status;
    private long orderTime;
    private long orderId;

    public Order(long payment, String status, long orderTime) {
        this.buyerId = ThreadLocalRandom.current().nextLong(1_000_000);
        this.payment = payment;
        this.status = status;
        this.orderTime = orderTime;
    }
}
