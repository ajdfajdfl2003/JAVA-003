package idv.angus.task02;

import idv.angus.task02.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Log4j2
public class InsertWithThreadAndPoolSize {
    private static final int poolSize = 20;
    private static final int times = 1_000_000;
    private static final int perThreadDoRecord = times / poolSize;
    private static final StopWatch watch = new StopWatch();
    private static MyDataSource dataSource;

    public static void main(String[] args) throws InterruptedException {
        dataSource = new MyDataSource(poolSize, true);
        final CountDownLatch countDownLatch = new CountDownLatch(poolSize);
        final ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        log.info("Start 1_000_000 record. " + perThreadDoRecord + " record per Thread");
        watch.start();

        IntStream.range(0, poolSize).forEach(value -> executorService.execute(() -> {
            try {
                insert(perThreadDoRecord);
            } catch (SQLException e) {
                log.error(e);
            } finally {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();
        watch.stop();
        log.info("Time Elapsed for 1_000_000 record. " + perThreadDoRecord + " record per Thread: " + watch.getTime() + "mills");
        executorService.shutdown();
    }

    private static void insert(int perThreadDoRecord) throws SQLException {
        String sql = "INSERT INTO `ORDER` (BUYER_ID, DETAIL_ID, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);

            for (long i = 0; i < perThreadDoRecord; i++) {
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
