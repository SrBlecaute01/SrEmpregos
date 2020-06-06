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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.isCancelled()) return;
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if(SrEmpregos.getInstance().getEmployeeManager().hasJob(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            JobType playerJob = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());

            if(!SrEmpregos.getInstance().getJobManager().isValidJobWorld(playerJob, p.getWorld().getName())) return;
            if (!playerJob.equals(JobType.MINER) && !playerJob.equals(JobType.DIGGER) && !playerJob.equals(JobType.FARMER) && !playerJob.equals(JobType.LUMBERJACK)) return;

            if (!SrEmpregos.getInstance().getJobManager().isValidJobMaterial(playerJob, e.getBlock().getType())) return;
            if(playerJob.equals(JobType.FARMER) && e.getBlock().getState().getData() instanceof Crops && !JobUtils.isGrown(e.getBlock())) return;

            Employee employee = SrEmpregos.getInstance().getEmployeeManager().getEmployeeAccount(p.getName());
            employee.setCompletedMeta(employee.getCompletedMeta() + 1);
            employee.setCurrentMeta(employee.getCurrentMeta() + 1);
            JobUtils.executeEvents(p, employee, playerJob);
        }
    }
}