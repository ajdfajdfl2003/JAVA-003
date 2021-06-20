package idv.angus.task09;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class T1Repo {
    @Autowired
    private T1Dao dao;

    public List<Integer> findAll() {
        return dao.findAll();
    }

    public void add(int value) {
        dao.add(value);
    }
}
