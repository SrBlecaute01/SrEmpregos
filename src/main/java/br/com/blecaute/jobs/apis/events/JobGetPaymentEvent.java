package br.com.blecaute.jobs.apis.events;

import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class JobGetPaymentEvent extends PlayerEvent {

    @Getter private Employee employee;
    @Getter private JobType jobType;

    private static final HandlerList handler = new HandlerList();

    public JobGetPaymentEvent(Player p, Employee employee, JobType jobType) {
        super(p);
        this.employee = employee;
        this.jobType = jobType;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}