package br.com.blecaute.jobs.apis.events;

import br.com.blecaute.jobs.model.enuns.JobType;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class JobContractEvent extends PlayerEvent {

    @Getter private JobType jobType;
    private static final HandlerList handler = new HandlerList();

    public JobContractEvent(Player p, JobType jobType) {
        super(p);
        this.jobType = jobType;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }
}