package idv.angus.task10;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UsingHikari {
    private static final MyMySqlDataSource dataSource = new MyMySqlDataSource();

    public static void main(String[] args) throws SQLException {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate(sql);
        }

    }

    private static void insert(MyTask10... myTask10s) throws SQLException {
        String sql = "INSERT INTO MYTASK10 " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
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
        try (Connection connection = dataSource.getConnection();
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCreate)) {
            // ?????????????????????????????? Transaction??????????????? commit ??? ????????????????????? true)
            connection.setAutoCommit(true);
            preparedStatement.execute();
        }
    }

    private static class MyMySqlDataSource {
        private static final String url = "jdbc:mysql://localhost:3306/test";
        private static final String user = "root";
        private static final String password = "";
        private final HikariDataSource ds;

        public MyMySqlDataSource() {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setMaximumPoolSize(5);
            ds = new HikariDataSource(config);
        }

        public Connection getConnection() throws SQLException {
            return ds.getConnection();
        }
    }
}
