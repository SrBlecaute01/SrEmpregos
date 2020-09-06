package br.com.blecaute.jobs.database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database {

    public abstract void close();

    public abstract Connection getConnection() throws SQLException;
}