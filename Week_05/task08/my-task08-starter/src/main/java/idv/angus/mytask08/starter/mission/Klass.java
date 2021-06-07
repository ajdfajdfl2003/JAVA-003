package idv.angus.mytask08.starter.mission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@Log4j2
@AllArgsConstructor
public class Klass {
    List<Student> students;

    public void dong() {
        log.info("[my task 08 spring boot starter] This class have these: "+this.getStudents());
    }
}
