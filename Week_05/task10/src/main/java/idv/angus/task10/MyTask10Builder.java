package idv.angus.task10;

public class MyTask10Builder {
    private String brand = "default brand";
    private int year = 2020;
    private int number = 99;
    private int value = 1000;
    private String playerName = "Unknown player";

    public MyTask10Builder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public MyTask10Builder setYear(int year) {
        this.year = year;
        return this;
    }

    public MyTask10Builder setNumber(int number) {
        this.number = number;
        return this;
    }

    public MyTask10Builder setValue(int value) {
        this.value = value;
        return this;
    }

    public MyTask10Builder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public MyTask10 build() {
        return new MyTask10(brand, year, number, value, playerName);
    }
}
