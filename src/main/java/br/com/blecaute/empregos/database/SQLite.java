package br.com.blecaute.empregos.database;

import br.com.blecaute.empregos.SrEmpregos;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite extends Database {

    private final SQLiteDataSource source = new SQLiteDataSource();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public SQLite() {
        try {
            File pasta = new File(SrEmpregos.getInstance().getDataFolder() + File.separator + "database");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

        } catch (Throwable e) {
            SrEmpregos.info("§cOcorreu um erro ao tentar criar a pasta da database: " + e.getMessage());
            return;
        }
        source.setUrl("jdbc:sqlite:" + new File(SrEmpregos.getInstance().getDataFolder() + File.separator + "database", "dados.db"));
    }

    @Override
    public void close() { }

    @Override
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
