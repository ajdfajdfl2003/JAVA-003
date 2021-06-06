package idv.angus.task10;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyTask10 {
    private final String brand;
    private final int year;
    private final int number;
    private final int value;
    private final String playerName;

    public MyTask10(String brand, int year, int number, int value, String playerName) {
        this.brand = brand;
        this.year = year;
        this.number = number;
        this.value = value;
        this.playerName = playerName;
    }
}
