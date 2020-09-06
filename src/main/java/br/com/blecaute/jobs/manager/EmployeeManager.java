package br.com.blecaute.jobs.manager;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.apis.events.JobContractEvent;
import br.com.blecaute.jobs.apis.events.JobDismissEvent;
import br.com.blecaute.jobs.apis.events.JobGetPaymentEvent;
import br.com.blecaute.jobs.apis.events.JobQuestCompleteEvent;
import br.com.blecaute.jobs.dao.EmployeeDao;
import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.Quest;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.blecaute.jobs.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class EmployeeManager {

    private final JobDao JOB_DAO = JobDao.getInstance();
    private final EmployeeDao EMPLOYEE_DAO = EmployeeDao.getInstance();

    public boolean hasJob(String player) {
        return EMPLOYEE_DAO.has(player);
    }

    public Employee getEmployee(String player) {
        return EMPLOYEE_DAO.get(player);
    }

    public boolean isInJob(String p, JobType job) {
        Employee employee = getEmployee(p);
        return employee != null && employee.getJob().equals(job);
    }

    public JobType getEmployeeJob(String player) {
        Employee employee = getEmployee(player);
        return employee != null ? employee.getJob() : null;
    }

    public boolean contractPlayer(Player player, JobType job) {
        if(hasJob(player.getName())) return false;

        Employee employee = new Employee(player.getName(), job, JOB_DAO.get(job).getSalary());
        EMPLOYEE_DAO.add(employee);

        JobContractEvent event = new JobContractEvent(player, job);
        Bukkit.getPluginManager().callEvent(event);
        return true;
    }

    public boolean dismissPlayer(String player) {
        if(!hasJob(player)) return false;

        Employee employee = getEmployee(player);
        JobDismissEvent event = new JobDismissEvent(employee, employee.getJob());
        Bukkit.getPluginManager().callEvent(event);

        EMPLOYEE_DAO.remove(player);
        return true;
    }

    public List<Quest> getQuests(JobType type) {
        Job job = getJob(type);
        return job.getQuests();
    }

    public Quest getCurrentQuest(Employee employee) {
        Job job = getJob(employee.getJob());
        return job.getQuests().stream()
                .filter(quest -> !employee.getQuestsCompleted().contains(quest.getId()))
                .findFirst()
                .orElse(null);
    }

    public Quest getNextQuest(Employee employee) {
        Quest current = getCurrentQuest(employee);
        if(current != null) {
            return getNextQuest(current);
        }
        return null;
    }

    public Quest getNextQuest(Quest quest) {
        Job job = getJob(quest.getJob());
        return job.getQuests().stream()
                .filter(value -> !value.getId().equals(quest.getId()) && value.getPosition() > quest.getPosition())
                .findFirst()
                .orElse(null);
    }

    public Job getJob(JobType job) {
        return JOB_DAO.get(job);
    }

    public void execute(Player player, Employee employee, JobType type) {
        Quest quest = getCurrentQuest(employee);
        if (quest != null && employee.getTotalMeta() >= quest.getMeta()) {
            JobQuestCompleteEvent event = new JobQuestCompleteEvent(player, quest, employee);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                if (quest.isIncreaseSalary()) {
                    employee.addSalary(quest.getSalary());
                }

                if (quest.getMessageComplete() != null) {
                    quest.getMessageComplete().stream()
                            .map(m -> m.replace("@player", player.getName()))
                            .forEach(player::sendMessage);
                }

                if (quest.getCommands() != null) {
                    quest.getCommands().stream()
                            .map(m -> m.replace("@player", player.getName()))
                            .forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
                }

                employee.setTotalMeta(0);
                employee.addQuest(quest);
            }
        }

        Job job = getJob(type);
        if(employee.getCurrentMeta() >= job.getMeta()) {
            JobGetPaymentEvent event = new JobGetPaymentEvent(player, employee, type);
            Bukkit.getPluginManager().callEvent(event);

            SrEmpregos.getInstance().getEconomy().depositPlayer(player, employee.getSalary().doubleValue());
            employee.setCurrentMeta(0);

            player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 1.0F);
            player.sendMessage(String.join("\n", Config.PAYMENT_MESSAGE.list)
                    .replace("@salary", NumberUtils.format(employee.getSalary().doubleValue())));
        }
    }
}