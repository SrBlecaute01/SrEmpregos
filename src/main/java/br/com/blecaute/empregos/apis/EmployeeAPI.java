package br.com.blecaute.empregos.apis;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.objects.Quest;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeAPI {

    private EmployeeAPI() {}

    /**
     *
     * @param p jogador a ser verificado
     * @return verifica se o jogador tem algum trabalho
     */

    public static Boolean hasJob(String p) {
        return SrEmpregos.getEmployeeManager().hasJob(p);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna a conta do jogador
     */

    public static Employee getEmployeeAccount(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeAccount(p);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @param job trabalho a ser verificado
     * @return verificar se o jogador está em um determinado trablho
     */

    public static Boolean isInJob(String p, JobType job) {
        return SrEmpregos.getEmployeeManager().isInJob(p, job);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna o trabalho atual do jogador
     */

    public static JobType getEmployeeJob(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeJob(p);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna o salário atual do jogador
     */

    public static BigDecimal getEmployeeSalary(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeSalary(p);
    }

    /**
     *
     * @param p jogador para definir o salário
     * @param salary salário a ser dado
     * @return define o salário do jogador
     */

    public static Boolean setEmployeeSalary(String p, BigDecimal salary) {
        return SrEmpregos.getEmployeeManager().setEmployeeSalary(p, salary);
    }

    /**
     *
     * @param p jogador para adicionar o salário
     * @param salary salário
     * @return aumenta o salário do jogador
     */

    public static Boolean addEmployeeSalary(String p, BigDecimal salary) {
        return SrEmpregos.getEmployeeManager().addEmployeeSalary(p, salary);
    }

    /**
     *
     * @param p jogador a ser removido
     * @param salary salário a ser removido
     * @return diminui o salário do jogador
     */

    public static Boolean removeEmployeeSalary(String p, BigDecimal salary) {
        return SrEmpregos.getEmployeeManager().removeEmployeeSalary(p, salary);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna a contagem para pegar o salário
     */

    public static Integer getEmployeeCurrentMeta(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeCurrentMeta(p);
    }

    /**
     *
     * @param p jogador a ter sua meta modificada
     * @param value nova meta do jogador
     * @return define a contagem para pegar osalário
     */

    public static Boolean setEmployeeCurrentMeta(String p, Integer value) {
        return SrEmpregos.getEmployeeManager().setEmployeeCurrentMeta(p, value);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna total de ações efetuadas (cortar, quebrar, pescar etc.)
     */

    public static Integer getEmployeeMeta(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeMeta(p);
    }

    /**
     *
     * @param p jogador a ter sua meta modificada
     * @param value nova meta a ser dada
     * @return define o total de ações efetuadas
     */

    public static Boolean setEmployeeMeta(String p, Integer value) {
        return SrEmpregos.getEmployeeManager().setEmployeeMeta(p, value);
    }

    /**
     *
     * @param p jogador a ser adicionado
     * @param job emprego que será dado ao jogador
     * @return adiciona um emprego ao jogador
     */

    public Boolean addJob(Player p, JobType job) {
        return SrEmpregos.getEmployeeManager().contractPlayer(p, job);
    }

    /**
     *
     * @param p jogador a ser demitido
     * @return demite o jogador do seu emprego atual
     */

    public Boolean dismissPlayer(String p) {
        return SrEmpregos.getEmployeeManager().dismissPlayer(p);
    }

    /**
     *
     * @param p jogador a ter seu trabalho modificado
     * @param job novo trabalho do jogador
     * @return define o emprego do jogador
     */

    public Boolean setEmployeeJob(String p, JobType job) {
        return SrEmpregos.getEmployeeManager().setEmployeeJob(p, job);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @return retorna a lista de ids das quests completadas pelo jogador
     */

    public static List<String> getEmployeeQuests(String p) {
        return SrEmpregos.getEmployeeManager().getEmployeeQuests(p);
    }

    /**
     *
     * @param p jogador a ter suas quests modificadas
     * @param q quest a ser dada
     * @return adiciona uma quest ao jogador
     */

    public static Boolean addEmployeeQuests(String p, Quest q) {
        return SrEmpregos.getEmployeeManager().addEmployeeQuests(p, q);
    }

    /**
     *
     * @param p jogador a ter a quest removida
     * @param q quest a ser removida
     * @return remove uma quest do jogador
     */

    public static Boolean removeEmployeeQuest(String p, Quest q) {
        return SrEmpregos.getEmployeeManager().removeEmployeeQuest(p, q);
    }

    /**
     *
     * @param p jogador a ser verificado
     * @param q quest a ser verificada
     * @return verifica se a quest do jogador está completada
     */

    public static Boolean isCompleted(String p, Quest q) {
        return SrEmpregos.getEmployeeManager().isCompleted(p, q);
    }
}