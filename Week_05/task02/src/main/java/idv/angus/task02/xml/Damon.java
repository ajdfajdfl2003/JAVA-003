package idv.angus.task02.xml;

import idv.angus.task02.components.User;
import idv.angus.task02.components.keycaps.KeyCap;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Damon implements User {
    Keyboard keyboard;
    String name;

    @Override
    public void useKeyBoard() {
        log.info(name + " is starting to use keyboard.");
        keyboard.getKeyCaps().forEach(KeyCap::press);
        log.info(name + " has been used keyboard.");
    }
}
