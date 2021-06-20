package idv.angus.task02.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Order {
    private final long buyerId;
    private final long detailId;
    private final long payment;
    private final String status;
    private final long orderTime;
    private long id;

    public Order() {
        this.buyerId = 1;
        this.detailId = 1;
        this.payment = 1000000;
        this.status = "NotFinish";
        this.orderTime = Instant.now().toEpochMilli();
    }
}
