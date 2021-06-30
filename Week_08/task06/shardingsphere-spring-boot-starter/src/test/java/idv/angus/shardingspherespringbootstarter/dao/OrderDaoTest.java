package idv.angus.shardingspherespringbootstarter.dao;


import idv.angus.shardingspherespringbootstarter.TransactionConfiguration;
import idv.angus.shardingspherespringbootstarter.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderDaoTest.class)
@Import(TransactionConfiguration.class)
@ComponentScan(basePackages = "idv.angus.shardingspherespringbootstarter")
@ActiveProfiles("sharding-databases-tables")
@Log4j2
public class OrderDaoTest {
    @Autowired
    private OrderDao dao;

    @After
    public void tearDown() {
        dao.clearDB();
    }

    @Test
    public void insertFailed() {
        try {
            log.info("Before insert: {}", dao.query());
            dao.duplicatedInsert(new Order(1_000_000L, "not check", Instant.now().toEpochMilli()));
            fail("should error!!!");
        } catch (Exception e) {
            log.error(e);
            assertEquals(0, dao.query().size());
        } finally {
            log.info("After insert: {}", dao.query());
        }
    }

    @Test
    public void insert() {
        log.info("Before insert: {}", dao.query());
        dao.insert(new Order(1_000_000L, "not check", Instant.now().toEpochMilli()));
        final List<Order> afterInsert = dao.query();
        log.info("After insert: {}", afterInsert);
        assertEquals(1, afterInsert.size());
    }
}