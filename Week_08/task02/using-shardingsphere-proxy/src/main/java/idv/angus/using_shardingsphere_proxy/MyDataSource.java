package idv.angus.using_shardingsphere_proxy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDataSource {
    private static final String url = "jdbc:mysql://localhost:3307/sharding_db";
    private static final String user = "root";
    private static final String password = "root";
    private final HikariDataSource ds;

    public MyDataSource(boolean useRewriteBatched) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url + (useRewriteBatched ? "?rewriteBatchedStatements=true" : ""));
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
