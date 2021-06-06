package idv.angus.task10;

import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UsingPrepareStatement {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        createTable();

        log.info(query());

        insert(
                new MyTask10("Yankees", 2020, 1, 10000, "Angus Wu"),
                new MyTask10("Yankees", 2021, 2, 20000, "Jason Wu")
        );

        insert(
                new MyTask10("Yankees", 2021, 4, 40000, "Peter Wu"),
                new MyTask10("VALUETOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOLONG", 2020, 3, 30000, "Error Wu")
        );

        log.info(query());

        delete();
    }

    private static void delete() throws SQLException {
        String sql = "DELETE FROM MYTASK10";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate(sql);
        }

    }

    private static void insert(MyTask10... myTask10s) throws SQLException {
        String sql = "INSERT INTO MYTASK10 " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (MyTask10 myTask10 : myTask10s) {
                preparedStatement.setString(1, myTask10.getBrand());
                preparedStatement.setInt(2, myTask10.getYear());
                preparedStatement.setInt(3, myTask10.getNumber());
                preparedStatement.setInt(4, myTask10.getValue());
                preparedStatement.setString(5, myTask10.getPlayerName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            log.error(e);
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }

    private static List<MyTask10> query() throws SQLException {
        List<MyTask10> results = new ArrayList<>();
        String sql = "SELECT * FROM MYTASK10";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate)) {
            // 顯式的表示我不需要做 Transaction，直接幫我 commit 吧 （雖然說預設是 true)
            connection.setAutoCommit(true);
            preparedStatement.execute();
        }
    }
}
