package br.com.blecaute.jobs.listeners;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.interfaces.Job;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {

    private final EmployeeManager MANAGER = SrEmpregos.getInstance().getEmployeeManager();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled()
                || player.getGameMode().equals(GameMode.CREATIVE)
                || !event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)
                || !(event.getCaught() instanceof Item)
                || !MANAGER.hasJob(player.getName())) return;

        Employee employee = MANAGER.getEmployee(player.getName());
        JobType jobType = employee.getJob();
        Job job = MANAGER.getJob(jobType);

        Item item = (Item) event.getCaught();
        String value = item.getItemStack().getType().name();
        if(job.getAction().equals(ActionType.FISH) && job.isValidWorld(player.getWorld()) && job.isValidValue(value)) {
            employee.setTotalMeta(employee.getTotalMeta() + 1);
            employee.setCurrentMeta(employee.getCurrentMeta() + 1);

            MANAGER.execute(player, employee, jobType);
        }
    }
}