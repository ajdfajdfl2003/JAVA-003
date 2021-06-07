package idv.angus.mytask08;

import idv.angus.mytask08.starter.mission.ISchool;
import idv.angus.mytask08.starter.mission.Student;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
@Log4j2
public class MyTask08Application {
    @Resource(name = "student100")
    Student student;

    @Resource
    private ISchool school;

    public static void main(String[] args) {
        SpringApplication.run(MyTask08Application.class, args);
    }

    @Bean
    public void doSomething() {
        school.ding();
        log.info(student);
    }
}
