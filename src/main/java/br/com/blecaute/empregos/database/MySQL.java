package br.com.blecaute.empregos.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL extends Database {

    private final HikariDataSource source;

    public MySQL(String host, String port, String database, String user, String password) {
        source = new HikariDataSource();
        source.setMaximumPoolSize(10);
        source.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        source.setUsername(user);
        source.setPassword(password);
        source.addDataSourceProperty("autoReconnect", "true");
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