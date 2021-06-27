package idv.angus.using_shardingsphere_proxy;

import idv.angus.using_shardingsphere_proxy.dto.Order;
import idv.angus.using_shardingsphere_proxy.dto.OrderDetail;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Log4j2
public class InsertWithProxy {
    private static final MyDataSource dataSource = new MyDataSource(false);

    public static void main(String[] args) throws DataAccessException {
        final long orderId1 = insert_order(new Order(1_000_000L, "not check", Instant.now().toEpochMilli()));
        insert_order_detail(new OrderDetail(orderId1, 1234, 2, 500_000));
        insert_order_detail(new OrderDetail(orderId1, 1235, 5, 500_000));

        List<Order> orders = query_order();
    }

    private static List<Order> query_order() {
        return null;
    }

    private static void insert_order_detail(OrderDetail orderDetail) throws DataAccessException {
        String sql = "INSERT INTO `order_detail` (order_id, PRODUCT_ID, AMOUNT, PAYMENT)" +
                "VALUES (?, ?, ?, ?);";
        try {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                connection.setAutoCommit(true);

                preparedStatement.setLong(1, orderDetail.getOrderId());
                preparedStatement.setLong(2, orderDetail.getProductId());
                preparedStatement.setLong(3, orderDetail.getAmount());
                preparedStatement.setLong(4, orderDetail.getPayment());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            log.error(e);
            throw new DataAccessException(e);
        }

    }

    private static long insert_order(Order order) throws DataAccessException {
        String sql = "INSERT INTO `order` (buyer_id, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?);";
        try {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(true);

                preparedStatement.setLong(1, order.getBuyerId());
                preparedStatement.setLong(2, order.getPayment());
                preparedStatement.setString(3, order.getStatus());
                preparedStatement.setLong(4, order.getOrderTime());
                preparedStatement.execute();

                try (ResultSet orderId = preparedStatement.getGeneratedKeys()) {
                    if (orderId.next()) {
                        final long id = orderId.getLong(1);
                        log.info("orderId: " + id);
                        return id;
                    } else {
                        throw new DataAccessException("Creating user failed, no ID obtained.");
                    }
                }
            }

        } catch (SQLException e) {
            log.error(e);
            throw new DataAccessException(e);
        }
    }

    private static class DataAccessException extends Exception {
        public DataAccessException(Throwable e) {
            super(e);
        }

        public DataAccessException(String message) {
            super(message);
        }
    }
}
