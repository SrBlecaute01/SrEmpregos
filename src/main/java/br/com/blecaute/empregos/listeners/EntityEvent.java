package br.com.blecaute.empregos.listeners;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.utils.JobUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            if(p.getGameMode().equals(GameMode.CREATIVE)) return;
            if(SrEmpregos.getEmployeeManager().hasJob(p.getName())) {
                JobType playerJob = SrEmpregos.getEmployeeManager().getEmployeeJob(p.getName());

                if(!SrEmpregos.getJobManager().isValidJobWorld(playerJob, p.getWorld().getName())) return;
                if (!playerJob.equals(JobType.KILLER) && !playerJob.equals(JobType.HUNTER)) return;
                if (!SrEmpregos.getJobManager().isValidJobEntity(playerJob, e.getEntityType())) return;

                Employee employee = SrEmpregos.getEmployeeManager().getEmployeeAccount(p.getName());
                employee.setCompletedMeta(employee.getCompletedMeta() + 1);
                employee.setCurrentMeta(employee.getCurrentMeta() + 1);
                JobUtils.executeEvents(p, employee, playerJob);
            }
        }
    }
}