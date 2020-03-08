package br.com.blecaute.empregos.apis.events;

import br.com.blecaute.empregos.enums.JobType;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class JobContractEvent extends PlayerEvent {

    @Getter private JobType job;
    private static final HandlerList handler = new HandlerList();

    public JobContractEvent(Player p, JobType job) {
        super(p);
        this.job = job;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }
}
