package br.com.blecaute.empregos.apis.events;

import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class JobGetPaymentEvent extends PlayerEvent {

    @Getter private Employee employee;
    @Getter private JobType job;

    private static final HandlerList handler = new HandlerList();

    public JobGetPaymentEvent(Player p, Employee employee, JobType job) {
        super(p);
        this.employee = employee;
        this.job = job;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }
}