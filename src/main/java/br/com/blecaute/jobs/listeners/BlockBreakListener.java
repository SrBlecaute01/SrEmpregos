package br.com.blecaute.jobs.listeners;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.interfaces.Job;
import org.bukkit.CropState;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

public class BlockBreakListener implements Listener {

    private final EmployeeManager MANAGER = SrEmpregos.getInstance().getEmployeeManager();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled()
                || player.getGameMode().equals(GameMode.CREATIVE)
                || !MANAGER.hasJob(player.getName())) return;

        Employee employee = MANAGER.getEmployee(player.getName());
        JobType jobType = employee.getJob();
        Job job = MANAGER.getJob(jobType);

        String value = event.getBlock().getType().name();
        if(jobType.equals(JobType.FARMER) && !isGrown(event.getBlock())) return;
        if(job.getAction().equals(ActionType.BREAK) && job.isValidValue(value) && job.isValidWorld(player.getWorld())) {
            employee.setTotalMeta(employee.getTotalMeta() + 1);
            employee.setCurrentMeta(employee.getCurrentMeta() + 1);

            MANAGER.execute(player, employee, jobType);
        }
    }

    private boolean isGrown(Block block) {
        MaterialData materialData = block.getState().getData();
        if(materialData instanceof Crops) {
            Crops crops = (Crops) materialData;
            return crops.getState().equals(CropState.RIPE);
        }
        return false;
    }
}