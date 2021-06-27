package idv.angus.using_shardingsphere_proxy.dao;

import idv.angus.using_shardingsphere_proxy.MyDataSource;
import idv.angus.using_shardingsphere_proxy.dto.OrderDetail;
import idv.angus.using_shardingsphere_proxy.exception.DataAccessException;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OrderDetailDao {
    private final MyDataSource dataSource;

    public OrderDetailDao(MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<OrderDetail> query() throws DataAccessException {
        String sql = "select * from `order_detail`";
        final List<OrderDetail> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                final OrderDetail item = OrderDetail.builder()
                        .oDetailId(resultSet.getLong("o_detail_id"))
                        .orderId(resultSet.getLong("order_id"))
                        .productId(resultSet.getLong("PRODUCT_ID"))
                        .amount(resultSet.getLong("AMOUNT"))
                        .payment(resultSet.getLong("PAYMENT"))
                        .build();
                results.add(item);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DataAccessException(e);
        }
        return results;
    }

    public void insert(OrderDetail orderDetail) throws DataAccessException {
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

    public void delete(long orderId) throws DataAccessException {
        String sql = "DELETE FROM `order_detail` WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e);
            throw new DataAccessException(e);
        }
    }
}
