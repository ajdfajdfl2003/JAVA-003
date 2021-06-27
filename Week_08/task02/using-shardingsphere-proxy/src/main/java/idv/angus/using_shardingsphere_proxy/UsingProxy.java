package idv.angus.using_shardingsphere_proxy;

import idv.angus.using_shardingsphere_proxy.dao.OrderDao;
import idv.angus.using_shardingsphere_proxy.dao.OrderDetailDao;
import idv.angus.using_shardingsphere_proxy.dto.Order;
import idv.angus.using_shardingsphere_proxy.dto.OrderDetail;
import idv.angus.using_shardingsphere_proxy.exception.DataAccessException;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;

@Log4j2
public class UsingProxy {
    private static final MyDataSource dataSource = new MyDataSource(false);
    private static final OrderDao orderDao = new OrderDao(dataSource);
    private static final OrderDetailDao orderDetailDao = new OrderDetailDao(dataSource);

    public static void main(String[] args) throws DataAccessException {
        final long orderId = orderDao.insert(new Order(1_000_000L, "not check", Instant.now().toEpochMilli()));

        orderDetailDao.insert(new OrderDetail(orderId, 1234, 2, 500_000));
        orderDetailDao.insert(new OrderDetail(orderId, 1235, 5, 500_000));

        log.info(orderDao.query());
        log.info(orderDetailDao.query());

        orderDao.update(orderId, "check");

        log.info(orderDao.query());
    }
}
