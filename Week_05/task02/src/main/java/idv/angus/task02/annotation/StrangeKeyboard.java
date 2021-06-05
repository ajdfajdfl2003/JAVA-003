package idv.angus.task02.annotation;

import idv.angus.task02.components.keycaps.KeyCap;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

@Data
public class StrangeKeyboard {
    @Autowired
    private List<KeyCap> lefKeyCaps;
    @Resource(name = "rightKeyCap")
    private List<KeyCap> rightKeyCaps;
}
