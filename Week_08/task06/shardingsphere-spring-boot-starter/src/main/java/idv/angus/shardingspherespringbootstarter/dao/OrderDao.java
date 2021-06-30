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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Log4j2
public class OrderDao {
    private final JdbcTemplate jdbcTemplate;

    public OrderDao(@Autowired final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void duplicatedInsert(Order order) {
        String sql = "INSERT INTO `order` (order_id, buyer_id, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, 1);
            ps.setLong(2, order.getBuyerId());
            ps.setLong(3, order.getPayment());
            ps.setString(4, order.getStatus());
            ps.setLong(5, order.getOrderTime());
        });
        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, 1);
            ps.setLong(2, order.getBuyerId());
            ps.setLong(3, order.getPayment());
            ps.setString(4, order.getStatus());
            ps.setLong(5, order.getOrderTime());
        });
//        jdbcTemplate.update(sql, ps -> doInsertPrimaryKey(order, ps));
    }

    private void doInsertPrimaryKey(Order order, PreparedStatement ps) throws SQLException {
        for (int i = 1; i < 3; i++) {
            ps.setLong(1, i);
            ps.setLong(2, order.getBuyerId());
            ps.setLong(3, order.getPayment());
            ps.setString(4, order.getStatus());
            ps.setLong(5, order.getOrderTime());
            ps.executeUpdate();
        }
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insert(Order order) {
        String sql = "INSERT INTO `order` (order_id, buyer_id, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, ps -> {
            ps.setLong(1, ThreadLocalRandom.current().nextLong(0, 1_000_000));
            ps.setLong(2, order.getBuyerId());
            ps.setLong(3, order.getPayment());
            ps.setString(4, order.getStatus());
            ps.setLong(5, order.getOrderTime());
        });

        log.info("Transaction Type: {}", TransactionTypeHolder.get());
    }

    public List<Order> query() {
        return jdbcTemplate.query("SELECT * FROM `order`", (rs, rowNum) -> Order.builder()
                .orderId(rs.getLong("order_id"))
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
