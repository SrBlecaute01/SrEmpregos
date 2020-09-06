package br.com.blecaute.jobs.listeners;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.interfaces.Job;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final EmployeeManager MANAGER = SrEmpregos.getInstance().getEmployeeManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if(player == null || player.getGameMode().equals(GameMode.CREATIVE)
                || !MANAGER.hasJob(player.getName())) return;

        Employee employee = MANAGER.getEmployee(player.getName());
        JobType jobType = employee.getJob();
        Job job = MANAGER.getJob(jobType);

        String value = event.getEntityType().name();
        if(job.getAction().equals(ActionType.KILL) && job.isValidWorld(player.getWorld()) && job.isValidValue(value)) {
            employee.setTotalMeta(employee.getTotalMeta() + 1);
            employee.setCurrentMeta(employee.getCurrentMeta() + 1);

            MANAGER.execute(player, employee, jobType);
        }
    }
}