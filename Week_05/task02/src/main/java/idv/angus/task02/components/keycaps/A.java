package idv.angus.task02.components.keycaps;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class A implements KeyCap {
    @Override
    public void press() {
        log.info("You press A key");
    }
}
