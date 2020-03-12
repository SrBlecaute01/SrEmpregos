package br.com.blecaute.empregos.manager;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.utils.JobUtils;
import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SQLManager {

    private final String table = "`SrEmpregos`";

    public void createTable(String table, String column) {
        try (Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
            c.createStatement().execute("CREATE TABLE IF NOT EXISTS " + table + " (" + column + ")");
        } catch (SQLException e) {
            SrEmpregos.info("§cOcorreu um erro ao tentar criar a tabela do plugin (" + e.getMessage() + ")");
        }
    }

    public List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<>();
        try (Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
            try(ResultSet rs = c.createStatement().executeQuery("SELECT * FROM " + table)) {
                while(rs.next()) {
                    String name = rs.getString("name");
                    JobType job;

                    try {
                        job = JobType.valueOf(rs.getString("job"));
                    } catch (IllegalArgumentException e) {
                        SrEmpregos.info("§cNão foi possível carregar os dados do jogador " + name);
                        continue;
                    }

                    BigDecimal balance = new BigDecimal(rs.getString("salary"));
                    int current = rs.getInt("current");
                    int meta = rs.getInt("total");
                    List<String> quests = JobUtils.deserializeQuests(rs.getString("quests"));

                    list.add(new Employee(name, job, balance, current, meta, quests));
                }
            }

        } catch (SQLException e) {
            SrEmpregos.info("§cOcorreu um erro ao tentar puxar os dados dos jogadores (" + e.getMessage() + ")");
        }
        return list;
    }

    public void saveEmployees() {
        ImmutableList<Employee> employees = ImmutableList.copyOf(SrEmpregos.getInstance().getEmployees());
        try (Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
            for(Employee employee : employees) {
                try(PreparedStatement ps = c.prepareStatement("SELECT * FROM " + table + " WHERE `name` = ?")) {
                    ps.setString(1, employee.getName());
                    try(ResultSet rs = ps.executeQuery()) {

                        if(rs.next()) {

                            try(PreparedStatement ps1 = c.prepareStatement("UPDATE " + table + " SET `job` = ?, `salary` = ?, `current` = ?, `total` = ?, `quests` = ? WHERE `name` = ?")) {
                                ps1.setString(1, employee.getJob().name());
                                ps1.setString(2, employee.getSalary().toPlainString());
                                ps1.setInt(3, employee.getCurrentMeta());
                                ps1.setInt(4, employee.getCompletedMeta());
                                ps1.setString(5, JobUtils.serializeQuests(employee.getQuestsComplete()));
                                ps1.setString(6, employee.getName());
                                ps1.executeUpdate();
                            }

                        } else {

                            try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO " + table + " (`name`, `job`, `salary`, `current`, `total`, `quests`) VALUES (?,?,?,?,?,?)")) {
                                ps2.setString(1, employee.getName());
                                ps2.setString(2, employee.getJob().name());
                                ps2.setString(3, employee.getSalary().toPlainString());
                                ps2.setInt(4, employee.getCurrentMeta());
                                ps2.setInt(5, employee.getCompletedMeta());
                                ps2.setString(6, JobUtils.serializeQuests(employee.getQuestsComplete()));
                                ps2.executeUpdate();
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            SrEmpregos.info("§cOcorreu um erro ao tentar salvar os dados dos jogadores (" + e.getMessage() + ")");
        }
    }

    public void deleteEmployee(Employee employee) {
        CompletableFuture.runAsync(() -> {
            try (Connection c = SrEmpregos.getInstance().getDb().getConnection()) {
                try(PreparedStatement ps = c.prepareStatement("DELETE FROM " + table + " WHERE `name` = ?")) {
                    ps.setString(1, employee.getName());
                    ps.executeUpdate();
                }

            } catch (SQLException e) {
                SrEmpregos.info("§cOcorreu um erro ao tentar deletar os dados do jogador " + employee.getName() + " (" + e.getMessage() + ")");
            }
        }, SrEmpregos.getInstance().getExecutor());
    }
}