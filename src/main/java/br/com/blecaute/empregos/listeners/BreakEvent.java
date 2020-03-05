package br.com.blecaute.empregos.listeners;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.utils.JobUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;

public class BreakEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if(SrEmpregos.getEmployeeManager().hasJob(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            JobType playerJob = SrEmpregos.getEmployeeManager().getEmployeeJob(p.getName());

            if(!SrEmpregos.getJobManager().isValidJobWorld(playerJob, p.getWorld().getName())) return;
            if(playerJob.equals(JobType.MINER) || playerJob.equals(JobType.DIGGER) || playerJob.equals(JobType.FARMER) || playerJob.equals(JobType.LUMBERJACK)) {
                if(SrEmpregos.getJobManager().isValidJobMaterial(playerJob, e.getBlock().getType())) {

                    if(playerJob.equals(JobType.FARMER)) {
                        if(e.getBlock().getState().getData() instanceof Crops && !JobUtils.isGrown(e.getBlock())) return;
                    }

                    Employee employee = SrEmpregos.getEmployeeManager().getEmployeeAccount(p.getName());
                    employee.setCompletedMeta(employee.getCompletedMeta() + 1);
                    employee.setCurrentMeta(employee.getCurrentMeta() + 1);
                    JobUtils.executeEvents(p, employee, playerJob);
                }
            }
        }
    }
}