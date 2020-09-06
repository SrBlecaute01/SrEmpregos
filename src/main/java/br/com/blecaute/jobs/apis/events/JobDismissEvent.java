package br.com.blecaute.jobs.apis.events;

import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Employee;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JobDismissEvent extends Event {

    @Getter private Employee employee;
    @Getter private JobType jobType;

    private static final HandlerList handler = new HandlerList();

    public JobDismissEvent(Employee employee, JobType jobType) {
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