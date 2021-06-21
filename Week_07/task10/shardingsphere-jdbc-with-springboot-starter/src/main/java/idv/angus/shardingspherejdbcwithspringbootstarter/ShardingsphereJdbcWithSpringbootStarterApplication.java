package idv.angus.shardingspherejdbcwithspringbootstarter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@Log4j2
public class ShardingsphereJdbcWithSpringbootStarterApplication {

    @Autowired
    private T1Repo repo;

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereJdbcWithSpringbootStarterApplication.class, args);
    }

    @Bean
    public void doSomething() {
        log.info("get record from t1 table: " + repo.findAll());
        repo.add(ThreadLocalRandom.current().nextInt(0, 100));
        log.info("get record from t1 table: " + repo.findAll());
    }
}
