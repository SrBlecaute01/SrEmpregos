package br.com.blecaute.jobs.model.jobs;

import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.Quest;
import br.com.blecaute.jobs.model.interfaces.Job;
import lombok.AllArgsConstructor;
import org.bukkit.World;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class Fisher implements Job {

    private String name;
    private String tag;
    private BigDecimal salary;
    private int meta;
    private List<String> worlds;
    private List<Quest> quests;
    private List<String> values;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPermission() {
        return "job.fisher";
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public JobType getType() {
        return JobType.FISHER;
    }

    @Override
    public ActionType getAction() {
        return ActionType.FISH;
    }

    @Override
    public BigDecimal getSalary() {
        return this.salary;
    }

    @Override
    public int getMeta() {
        return this.meta;
    }

    @Override
    public List<String> getWorlds() {
        return this.worlds;
    }

    @Override
    public List<Quest> getQuests() {
        this.quests.sort(Comparator.comparingInt(Quest::getPosition));
        return this.quests;
    }

    @Override
    public boolean isValidWorld(World world) {
        return worlds.contains(world.getName().toLowerCase());
    }

    @Override
    public boolean isValidValue(String value) {
        return values.contains(value.toUpperCase());
    }
}