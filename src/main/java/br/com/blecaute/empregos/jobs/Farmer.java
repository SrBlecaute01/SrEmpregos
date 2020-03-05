package br.com.blecaute.empregos.jobs;

import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Quest;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class Farmer implements Job {

    private String name;
    private String tag;
    private BigDecimal salary;
    private Integer meta;
    private List<String> worlds;
    private List<Quest> quests;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public JobType getType() {
        return JobType.FARMER;
    }

    @Override
    public BigDecimal getSalary() {
        return this.salary;
    }

    @Override
    public Integer getMeta() {
        return this.meta;
    }

    @Override
    public List<String> getWorlds() {
        return this.worlds;
    }

    @Override
    public List<Quest> getQuests() {
        return this.quests;
    }
}