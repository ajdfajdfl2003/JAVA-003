package idv.angus.using_shardingsphere_proxy.dto;

import lombok.Getter;

@Getter
public class OrderDetail {
    private final long orderId;
    private final long productId;
    private final long amount;
    private final long payment;
    private long oDetailId;

    public OrderDetail(long orderId, long productId, long amount, long payment) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.payment = payment;
    }
}
