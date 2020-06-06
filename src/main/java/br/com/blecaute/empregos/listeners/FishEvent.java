package br.com.blecaute.empregos.listeners;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.utils.JobUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent e) {
        if(e.isCancelled()) return;
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if(!e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) return;
        if(!(e.getCaught() instanceof Item)) return;

        if(SrEmpregos.getInstance().getEmployeeManager().hasJob(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            JobType playerJob = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());

            if(!SrEmpregos.getInstance().getJobManager().isValidJobWorld(playerJob, p.getWorld().getName())) return;
            if (!playerJob.equals(JobType.FISHER)) return;
            Item item = (Item) e.getCaught();

            if (!SrEmpregos.getInstance().getJobManager().isValidJobMaterial(playerJob, item.getItemStack().getType())) return;

            Employee employee = SrEmpregos.getInstance().getEmployeeManager().getEmployeeAccount(p.getName());
            employee.setCompletedMeta(employee.getCompletedMeta() + 1);
            employee.setCurrentMeta(employee.getCurrentMeta() + 1);
            JobUtils.executeEvents(p, employee, playerJob);
        }
    }
}