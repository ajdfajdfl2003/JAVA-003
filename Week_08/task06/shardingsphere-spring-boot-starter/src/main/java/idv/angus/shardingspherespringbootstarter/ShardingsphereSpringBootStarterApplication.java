package idv.angus.shardingspherespringbootstarter;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionConfiguration.class)
@Log4j2
public class ShardingsphereSpringBootStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereSpringBootStarterApplication.class, args);
    }
}
