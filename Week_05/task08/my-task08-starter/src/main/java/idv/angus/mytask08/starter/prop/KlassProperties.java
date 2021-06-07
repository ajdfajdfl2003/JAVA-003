package idv.angus.mytask08.starter.prop;

import idv.angus.mytask08.starter.mission.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "mytask08.klass")
@Getter
@Setter
public class KlassProperties {
    List<Student> students;
}
