package idv.angus.task02;

import idv.angus.task02.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j2
public class InsertAllBatch {
    private static final int times = 1_000_000;
    private static final StopWatch watch = new StopWatch();
    private static MyDataSource dataSource;

    public static void main(String[] args) throws SQLException {
        dataSource = new MyDataSource(true);
        log.info("Start 1_000_000 record, do insert as batch. After 1_000_000 record, do commit");
        watch.start();
        insert();
        watch.stop();
        log.info("Time Elapsed for 1_000_000 record, do insert as batch. After 1_000_000 record, do commit: " + watch.getTime() + "mills");
    }

    private static void insert() throws SQLException {
        String sql = "INSERT INTO `ORDER` (BUYER_ID, DETAIL_ID, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);

            for (long i = 0; i < times; i++) {
                final Order order = new Order();
                preparedStatement.setLong(1, order.getBuyerId());
                preparedStatement.setLong(2, order.getDetailId());
                preparedStatement.setLong(3, order.getPayment());
                preparedStatement.setString(4, order.getStatus());
                preparedStatement.setLong(5, order.getOrderTime());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            log.error(e);
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
