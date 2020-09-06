package br.com.blecaute.jobs.model;

import br.com.blecaute.jobs.model.enuns.JobType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {

    private final String name;
    private JobType job;
    private BigDecimal salary;
    private int currentMeta = 0;
    private int totalMeta = 0;
    private final List<String> questsCompleted;

    protected boolean saved = false;

    public Employee(String name, JobType job, BigDecimal salary) {
        this.name = name;
        this.job = job;
        this.salary = salary;

        this.questsCompleted = new ArrayList<>();
    }

    public Employee(String name, JobType job, BigDecimal salary, int currentMeta, int totalMeta, List<String> questsCompleted) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.currentMeta = currentMeta;
        this.totalMeta = totalMeta;
        this.questsCompleted = questsCompleted;
    }

    public void setJob(JobType job) {
        this.job = job;
        this.saved = false;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
        this.saved = false;
    }

    public void setCurrentMeta(int currentMeta) {
        this.currentMeta = currentMeta;
        this.saved = false;
    }

    public void setTotalMeta(int totalMeta) {
        this.totalMeta = totalMeta;
        this.saved = false;
    }

    public void addSalary(BigDecimal value) {
        this.salary = this.salary.add(value);
        this.saved = false;
    }

    public void removeSalary(BigDecimal value) {
        this.salary = this.salary.subtract(value);
        this.saved = false;
    }

    public boolean isCompleted(Quest quest) {
        return quest.getJob().equals(this.job) && questsCompleted.contains(quest.getId());
    }

    public void addQuest(Quest quest) {
        questsCompleted.add(quest.getId());
        this.saved = false;
    }

    public void removeQuest(Quest quest) {
        questsCompleted.removeIf(value -> value.equals(quest.getId()));
        this.saved = false;
    }
}