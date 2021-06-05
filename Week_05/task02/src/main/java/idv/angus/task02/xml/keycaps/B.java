package idv.angus.task02.xml.keycaps;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class B implements KeyCap {
    @Override
    public void press() {
        log.info("You press B key");
    }
}
