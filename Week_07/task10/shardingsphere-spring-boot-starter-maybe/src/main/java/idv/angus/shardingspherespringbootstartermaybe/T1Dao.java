package idv.angus.shardingspherespringbootstartermaybe;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class T1Dao {
    @Resource
    @Qualifier("shardingSphereDataSource")
    private DataSource dataSource;

    public List<Integer> findAll() {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        return template.query("SELECT id FROM T1", (rs, i) -> rs.getInt("id"));
    }

    public void add(int value) {
        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("id", value);

        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        template.update("INSERT INTO T1(id) VALUES(:id)", parameters);
    }
}
