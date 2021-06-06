package idv.angus.task10;

import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UsingConnection {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        createTable();

        log.info(query());

        insert();

        log.info(query());

        delete();
    }

    private static void delete() throws SQLException {
        String sql = "DELETE FROM MYTASK10";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }

    }

    private static void insert() throws SQLException {
        String sql = "INSERT INTO MYTASK10 " +
                "VALUES ('New York Yankees', 2020, 9, 990, 'Angus Wu')";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    private static List<MyTask10> query() throws SQLException {
        List<MyTask10> results = new ArrayList<>();
        String sql = "SELECT * FROM MYTASK10";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                final MyTask10 myTask10 = new MyTask10Builder()
                        .setBrand(resultSet.getString("brand"))
                        .setYear(resultSet.getInt("year"))
                        .setNumber(resultSet.getInt("number"))
                        .setValue(resultSet.getInt("value"))
                        .setPlayerName(resultSet.getString("player_name"))
                        .build();
                results.add(myTask10);
            }
        }
        return results;
    }

    private static void createTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS MYTASK10"
                + "  (brand           VARCHAR(50),"
                + "   year            INTEGER,"
                + "   number          INTEGER,"
                + "   value           INTEGER,"
                + "   player_name     VARCHAR(50))";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreate);
        }
    }
}
