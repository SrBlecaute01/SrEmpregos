package br.com.blecaute.empregos.apis.events;

import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JobDismissEvent extends Event {

    @Getter private Employee employee;
    @Getter private JobType job;

    private static final HandlerList handler = new HandlerList();

    public JobDismissEvent(Employee employee, JobType job) {
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
