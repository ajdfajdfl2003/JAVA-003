package idv.angus.shardingspherespringbootstarter.dao;

import idv.angus.shardingspherespringbootstarter.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Log4j2
public class OrderDao {
    private final JdbcTemplate jdbcTemplate;

    public OrderDao(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWillFailed(){

    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insert(Order order) {
        String sql = "INSERT INTO `order` (buyer_id, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?);";

        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, order.getBuyerId());
            ps.setLong(2, order.getPayment());
            ps.setString(3, order.getStatus());
            ps.setLong(4, order.getOrderTime());
        });

        log.info(TransactionTypeHolder.get());
    }

    public List<Order> query() {
        return jdbcTemplate.query("SELECT * FROM `order`", (rs, rowNum) -> Order.builder()
                .oderId(rs.getLong("order_id"))
                .buyerId(rs.getLong("buyer_id"))
                .payment(rs.getLong("PAYMENT"))
                .status(rs.getString("STATUS"))
                .orderTime(rs.getLong("ORDER_TIME"))
                .build());
    }

    public void clearDB() {
        jdbcTemplate.execute("DELETE FROM `order`");
    }
}
