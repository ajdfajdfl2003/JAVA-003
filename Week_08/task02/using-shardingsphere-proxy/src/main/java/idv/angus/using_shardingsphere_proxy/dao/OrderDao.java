package idv.angus.using_shardingsphere_proxy.dao;

import idv.angus.using_shardingsphere_proxy.MyDataSource;
import idv.angus.using_shardingsphere_proxy.dto.Order;
import idv.angus.using_shardingsphere_proxy.exception.DataAccessException;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Log4j2
public class OrderDao {
    private final MyDataSource dataSource;

    public OrderDao(MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long insert(Order order) throws DataAccessException {
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

    public List<Order> query() throws DataAccessException {
        String sql = "select * from `order`";
        final List<Order> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                final Order item = Order.builder()
                        .oderId(resultSet.getLong("order_id"))
                        .buyerId(resultSet.getLong("buyer_id"))
                        .payment(resultSet.getLong("PAYMENT"))
                        .status(resultSet.getString("STATUS"))
                        .orderTime(resultSet.getLong("ORDER_TIME"))
                        .build();
                results.add(item);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DataAccessException(e);
        }
        return results;
    }
}
