package br.com.blecaute.jobs.model.interfaces;

import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Quest;
import org.bukkit.World;

import java.math.BigDecimal;
import java.util.List;

public interface Job {

    String getName();
    String getPermission();
    String getTag();

    JobType getType();
    ActionType getAction();

    BigDecimal getSalary();
    int getMeta();

    List<String> getWorlds();
    List<Quest> getQuests();

    boolean isValidWorld(World world);
    boolean isValidValue(String value);
}