package br.com.blecaute.empregos.apis.events;

import br.com.blecaute.empregos.objects.Employee;
import br.com.blecaute.empregos.objects.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class JobQuestCompleteEvent extends PlayerEvent implements Cancellable {

    @Getter private Quest quest;
    @Getter private Employee employee;

    private Boolean cancelled = false;
    private static final HandlerList handler = new HandlerList();

    public JobQuestCompleteEvent(Player p, Quest quest, Employee employee) {
        super(p);
        this.quest = quest;
        this.employee = employee;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}