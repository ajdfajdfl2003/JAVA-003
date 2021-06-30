package idv.angus.shardingspherespringbootstarter.dao;


import idv.angus.shardingspherespringbootstarter.TransactionConfiguration;
import idv.angus.shardingspherespringbootstarter.dto.Order;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderDaoTest.class)
@Import(TransactionConfiguration.class)
@ComponentScan(basePackages = "idv.angus.shardingspherespringbootstarter")
//@ActiveProfiles("sharding-databases-tables")
@Log4j2
public class OrderDaoTest {
    @Autowired
    private OrderDao dao;

    @Test
    public void insert() {
        dao.insert(new Order(1_000_000L, "not check", Instant.now().toEpochMilli()));
    }

    @Test
    public void query() {
        log.info(dao.query());
    }
}