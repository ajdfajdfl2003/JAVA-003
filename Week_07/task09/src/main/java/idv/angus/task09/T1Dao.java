package idv.angus.task09;

import idv.angus.task09.db.DynamicDataSource;
import idv.angus.task09.db.TargetDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class T1Dao {
    @Resource
    private DynamicDataSource dynamicDataSource;

    @TargetDataSource(value = "replicaDataSource")
    public List<Integer> findAll() {
        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dynamicDataSource);
        return template.query("SELECT id FROM T1", (rs, i) -> rs.getInt("id"));
    }

    @TargetDataSource(value = "mainDataSource")
    public void add(int value) {
        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("id", value);

        final NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dynamicDataSource);
        template.update("INSERT INTO T1(id) VALUES(:id)", parameters);
    }
}
