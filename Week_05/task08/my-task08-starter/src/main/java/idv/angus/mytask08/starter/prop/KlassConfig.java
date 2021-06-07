package idv.angus.mytask08.starter.prop;

import idv.angus.mytask08.starter.mission.Student;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KlassConfig {
    private final List<Student> students;

    public KlassConfig() {
        this.students = new ArrayList<>();
    }

    public void add(Student student) {
        students.add(student);
    }
}
