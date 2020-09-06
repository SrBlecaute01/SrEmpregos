package br.com.blecaute.jobs.database;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.utils.FileUtils;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite extends Database {

    protected final SQLiteDataSource source = new SQLiteDataSource();

    public SQLite() {
        FileUtils.createFolder("database");
        File file = new File(SrEmpregos.getInstance().getDataFolder() + File.separator + "database", "dados.db");

        SQLiteConfig config = new SQLiteConfig();
        config.setCacheSize(10000);

        source.setConfig(config);
        source.setUrl("jdbc:sqlite:" + file);
    }

    @Override
    public void close() { }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
