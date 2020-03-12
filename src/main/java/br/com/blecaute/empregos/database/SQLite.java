package br.com.blecaute.empregos.database;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.utils.FileUtils;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite extends Database {

    private final SQLiteDataSource source = new SQLiteDataSource();

    public SQLite() {
        FileUtils.createFolder("database");
        source.setUrl("jdbc:sqlite:" + new File(SrEmpregos.getInstance().getDataFolder() + File.separator + "database", "dados.db"));
    }

    @Override
    public void close() { }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
