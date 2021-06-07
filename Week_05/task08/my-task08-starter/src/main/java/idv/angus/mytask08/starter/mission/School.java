package idv.angus.mytask08.starter.mission;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
@Log4j2
public class School implements ISchool {
    @Autowired
    Klass class1;

    @Resource(name = "student100")
    Student student100;

    @Override
    public void ding() {
        log.info("[my task 08 spring boot starter] School has been notify that class dismissed.");
        log.info("[my task 08 spring boot starter] A Student is not in class, his name is " + this.student100);
        log.info("[my task 08 spring boot starter] Class1 have " + this.class1.getStudents().size() + " students.");
        class1.dong();
    }
}
