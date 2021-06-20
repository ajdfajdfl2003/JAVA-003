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
public class InsertWithThreadAs200000Batch {
    private static final MyDataSource dataSource = new MyDataSource();
    private static final int times = 1_000_000;
    private static final int perThreadDoRecord = 200_000;
    private static final int threadSize = times / perThreadDoRecord;
    private static final StopWatch watch = new StopWatch();

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        final ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        log.info("Start 1_000_000 record. 200_000 record per Thread");
        watch.start();

        IntStream.range(0, threadSize).forEach(value -> executorService.execute(() -> {
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
        log.info("Time Elapsed for 1_000_000 record. 200_000 record per Thread: " + watch.getTime() + "mills");
        executorService.shutdown();
    }

    private static void insert(int perThreadDoRecord) throws SQLException {
        String sql = "INSERT INTO `ORDER` (BUYER_ID, DETAIL_ID, PAYMENT, STATUS, ORDER_TIME)" +
                "VALUES (?, ?, ?, ?, ?);";

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
