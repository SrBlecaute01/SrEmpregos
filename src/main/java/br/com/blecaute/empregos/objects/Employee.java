package br.com.blecaute.empregos.objects;

import br.com.blecaute.empregos.enums.JobType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Employee {

    private String name;
    @Setter private JobType job;
    @Setter private BigDecimal salary;
    @Setter private Integer currentMeta = 0;
    @Setter private Integer completedMeta = 0;
    @Setter private List<String> questsComplete = new ArrayList<>();

    public Employee(String name, JobType job, BigDecimal salary) {
        this.name = name;
        this.job = job;
        this.salary = salary;
    }

    public Employee(String name, JobType job, BigDecimal salary, Integer currentMeta, Integer completedMeta, List<String> quests) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.currentMeta = currentMeta;
        this.completedMeta = completedMeta;
        this.questsComplete = quests;
    }

    public void addSalary(BigDecimal value) {
        this.salary = this.salary.add(value);
    }

    public void removeSalary(BigDecimal value) {
        this.salary = this.salary.subtract(value);
    }
}