package idv.angus.task02.xml;

import idv.angus.task02.components.keycaps.KeyCap;
import lombok.Data;

import java.util.List;

@Data
public class Keyboard {
    List<KeyCap> keyCaps;
}
