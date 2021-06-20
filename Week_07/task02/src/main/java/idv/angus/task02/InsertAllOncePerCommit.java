package idv.angus.task02;

import idv.angus.task02.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log4j2
public class InsertAllOncePerCommit {
    private static final MyDataSource dataSource = new MyDataSource();
    private static final int times = 1_000_000;
    private static final StopWatch watch = new StopWatch();

    public static void main(String[] args) {
        log.info("Start 1_000_000, one record one commit");
        watch.start();
        insert();
        watch.stop();
        log.info("Time Elapsed for 1_000_000, one record one commit: " + watch.getTime() + "mills");
    }

    private static void insert() {
        String sql = "INSERT INTO `ORDER` (BUYER_ID, DETAIL_ID, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (long i = 0; i < times; i++) {
                final Order order = new Order();
                preparedStatement.setLong(1, order.getBuyerId());
                preparedStatement.setLong(2, order.getDetailId());
                preparedStatement.setLong(3, order.getPayment());
                preparedStatement.setString(4, order.getStatus());
                preparedStatement.setLong(5, order.getOrderTime());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            log.error(e);
        }
    }
}
