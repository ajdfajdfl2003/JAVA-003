package idv.angus.shardingspherespringbootstartermaybe;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@Log4j2
public class ShardingsphereSpringBootStarterMaybeApplication {
    @Autowired
    private T1Repo repo;

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereSpringBootStarterMaybeApplication.class, args);
    }

    @Bean
    public void doSomeThing() {
        new Thread(() -> log.info("get record from t1 table: " + repo.findAll())).run();

        new Thread(() -> {
            repo.add(ThreadLocalRandom.current().nextInt(0, 100));
        }).run();

        new Thread(() -> log.info("get record from t1 table: " + repo.findAll())).run();
        new Thread(() -> log.info("get record from t1 table: " + repo.findAll())).run();
        new Thread(() -> log.info("get record from t1 table: " + repo.findAll())).run();
        new Thread(() -> log.info("get record from t1 table: " + repo.findAll())).run();
    }
}
