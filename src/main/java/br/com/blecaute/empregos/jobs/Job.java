package br.com.blecaute.empregos.jobs;

import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Quest;

import java.math.BigDecimal;
import java.util.List;

public interface Job {

    String getName();
    String getTag();
    JobType getType();
    BigDecimal getSalary();
    Integer getMeta();
    List<String> getWorlds();
    List<Quest> getQuests();
}