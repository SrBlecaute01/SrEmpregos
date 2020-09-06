package br.com.blecaute.jobs.process;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.dao.EmployeeDao;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.utils.StringUtils;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EmployeeProcess {

    public static void init() {
        try(Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
            c.createStatement().execute("CREATE TABLE IF NOT EXISTS `SrEmpregos` (" +
                    "`name` VARCHAR(36) NOT NULL, " +
                    "`job` TEXT NOT NULL, " +
                    "`salary` TEXT NOT NULL, " +
                    "`current` INTEGER NOT NULL, " +
                    "`total` INTEGER NOT NULL, " +
                    "`quests` TEXT, " +
                    "PRIMARY KEY (`name`))");

        } catch (SQLException e) {
            SrEmpregos.getInstance().getLogger().severe("Ocorreu um erro ao tentar criar a tabela do plugin: " + e.getMessage());
            SrEmpregos.getInstance().getLogger().severe("Desabilitando plugin...");
            Bukkit.getPluginManager().disablePlugin(SrEmpregos.getInstance());
        }
    }

    public static void process() {
        CompletableFuture.runAsync(() -> {
            EmployeeDao employeeDao = EmployeeDao.getInstance();
            try(Connection c = SrEmpregos.getInstance().getDb().getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM `SrEmpregos`");
                ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    JobType job = JobType.valueOf(rs.getString("job"));
                    BigDecimal balance = new BigDecimal(rs.getString("salary"));
                    int current = rs.getInt("current");
                    int meta = rs.getInt("total");
                    List<String> quests = StringUtils.fromString(rs.getString("quests"));

                    Employee employee = new Employee(name, job, balance, current, meta, quests);
                    employee.setSaved(true);

                    employeeDao.add(employee);
                }

            } catch (SQLException e) {
                SrEmpregos.getInstance().getLogger().severe("Ocorreu um erro ao tentar ler os dados dos jogadores: " + e.getMessage());
            }
        }, SrEmpregos.getInstance().getExecutor());
    }

    public static void save() {
        EmployeeDao employeeDao = EmployeeDao.getInstance();
        try (Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
            for(Employee employee : employeeDao.get()) {
                if(employee.isSaved()) continue;
                try(PreparedStatement ps = c.prepareStatement("SELECT * FROM `SrEmpregos` WHERE `name` = ?")) {
                    ps.setString(1, employee.getName());
                    try(ResultSet rs = ps.executeQuery()) {
                        if(rs.next()) {
                            try(PreparedStatement ps1 = c.prepareStatement("UPDATE `SrEmpregos` SET `job` = ?, `salary` = ?, `current` = ?, `total` = ?, `quests` = ? WHERE `name` = ?")) {
                                ps1.setString(1, employee.getJob().name());
                                ps1.setString(2, employee.getSalary().toPlainString());
                                ps1.setInt(3, employee.getCurrentMeta());
                                ps1.setInt(4, employee.getTotalMeta());
                                ps1.setString(5, StringUtils.toString(employee.getQuestsCompleted()));
                                ps1.setString(6, employee.getName());
                                ps1.executeUpdate();
                            }

                        } else {
                            try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO `SrEmpregos` (`name`, `job`, `salary`, `current`, `total`, `quests`) VALUES (?,?,?,?,?,?)")) {
                                ps2.setString(1, employee.getName());
                                ps2.setString(2, employee.getJob().name());
                                ps2.setString(3, employee.getSalary().toPlainString());
                                ps2.setInt(4, employee.getCurrentMeta());
                                ps2.setInt(5, employee.getTotalMeta());
                                ps2.setString(6, StringUtils.toString(employee.getQuestsCompleted()));
                                ps2.executeUpdate();
                            }
                        }

                        employee.setSaved(true);
                    }
                }
            }

        } catch (SQLException e) {
            SrEmpregos.getInstance().getLogger().severe("Ocorreu um erro ao tentar salvar os dados dos jogadores: " + e.getMessage());
        }
    }

    public static void saveAsync() {
        CompletableFuture.runAsync(EmployeeProcess::save, SrEmpregos.getInstance().getExecutor());
    }

    public static void delete(Employee employee) {
        CompletableFuture.runAsync(() -> {
            try(Connection c = SrEmpregos.getInstance().getDb().getConnection();
                PreparedStatement ps = c.prepareStatement("DELETE FROM `SrEmpregos` WHERE `name` = ?")) {
                ps.setString(1, employee.getName());
                ps.executeUpdate();

            } catch (SQLException e) {
                SrEmpregos.getInstance().getLogger().severe("Ocorreu um erro ao tentar deletar os dados de um jogador: " + e.getMessage());
            }
        }, SrEmpregos.getInstance().getExecutor());
    }
}