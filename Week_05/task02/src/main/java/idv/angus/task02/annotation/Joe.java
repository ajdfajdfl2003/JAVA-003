package idv.angus.task02.annotation;

import idv.angus.task02.components.User;
import idv.angus.task02.components.keycaps.KeyCap;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
@Log4j2
public class Joe implements User {
    // 詳細在 README
    @Autowired
    private StrangeKeyboard keyboard;

    @Resource(name = "myBackpack")
    private Backpack backpack;

    @Override
    public void useKeyBoard() {
        log.info("using right keyCaps");
        keyboard.getRightKeyCaps().forEach(KeyCap::press);
        log.info("using left keyCaps");
        keyboard.getLefKeyCaps().forEach(KeyCap::press);
        log.info("Oops! my backpack produce at: " + backpack.getProductionDate());
    }
}
