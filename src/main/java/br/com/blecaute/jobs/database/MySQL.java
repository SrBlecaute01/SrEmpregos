package br.com.blecaute.jobs.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL extends Database {

    protected final HikariDataSource source = new HikariDataSource();

    public MySQL(String host, String port, String database, String user, String password) {
        source.setPoolName("JobsPool");
        source.setMaxLifetime(1800000L);

        source.setMaximumPoolSize(10);
        source.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        source.setUsername(user);
        source.setPassword(password);

        source.addDataSourceProperty("autoReconnect", "true");
        source.addDataSourceProperty("useSLL", "false");
        source.addDataSourceProperty("characterEncoding", "utf-8");
        source.addDataSourceProperty("encoding", "UTF-8");
        source.addDataSourceProperty("useUnicode", "true");
        source.addDataSourceProperty("rewriteBatchedStatements", "true");
        source.addDataSourceProperty("jdbcCompliantTruncation", "false");
        source.addDataSourceProperty("cachePrepStmts", "true");
        source.addDataSourceProperty("prepStmtCacheSize", "275");
        source.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    @Override
    public void close() {
        source.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}