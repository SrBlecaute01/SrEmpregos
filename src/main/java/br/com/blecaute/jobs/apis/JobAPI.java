package br.com.blecaute.jobs.apis;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Quest;
import br.com.blecaute.jobs.model.interfaces.Job;
import org.bukkit.entity.Player;

import java.util.List;

public class JobAPI {
    
    protected final EmployeeManager manager;
    
    public JobAPI() {
        manager = SrEmpregos.getInstance().getEmployeeManager();
    }

    /**
     *  check if player has job
     *  
     * @param p the name of player
     * @return true if have any job
     */
    public boolean hasJob(String p) {
        return manager.hasJob(p);
    }

    /**
     * check if player is in job
     *
     * @param p the name of player
     * @param job the job
     * @return true if player is in job
     */
    public boolean isInJob(String p, JobType job) {
        return manager.isInJob(p, job);
    }

    /**
     * get employee
     *
     * @param p the name of player
     * @return the employee or null if not exists
     */
    public Employee getEmployee(String p) {
        return manager.getEmployee(p);
    }

    /**
     * get the type of job of player
     *
     * @param p the name of player
     * @return the type of job of player or null if employee not exists
     */
    public JobType getEmployeeJob(String p) {
        return manager.getEmployeeJob(p);
    }

    /**
     * contract player
     *
     * @param p the player
     * @param job the ype of job
     * @return true if player is contracted with success
     */
    public boolean contractPlayer(Player p, JobType job) {
        return manager.contractPlayer(p, job);
    }

    /**
     * dismiss player
     *
     * @param p the name of player
     */
    public boolean dismissPlayer(String p) {
        return manager.dismissPlayer(p);
    }

    /**
     * get list of quests
     *
     * @param type the type of job
     * @return the list of quests
     */
    public List<Quest> getQuests(JobType type) {
        return manager.getQuests(type);
    }

    /**
     *  get the current quest of employee
     *
     * @param employee the employee
     * @return the quest or null if not exists
     */
    public Quest getCurrentQuest(Employee employee) {
        return manager.getCurrentQuest(employee);
    }

    /**
     *  get next quest of employee
     *
     * @param employee the employee
     * @return the next quest or null if not exists
     */
    public Quest getNextQuest(Employee employee) {
        return manager.getNextQuest(employee);
    }

    /**
     *  get next quest
     *
     * @param quest the current quest
     * @return the next quest or null if not exists
     */
    public Quest getNextQuest(Quest quest) {
        return manager.getNextQuest(quest);
    }

    /**
     *  get job
     *
     * @param job the type of job
     * @return the job
     */
    public Job getJob(JobType job) {
        return manager.getJob(job);
    }
}