package br.com.blecaute.jobs.dao;

import br.com.blecaute.jobs.model.abstracts.Dao;
import br.com.blecaute.jobs.model.enuns.ActionType;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.interfaces.Job;
import lombok.Getter;

public class JobDao extends Dao<JobType, Job> {

    @Getter private static final JobDao instance = new JobDao();

    @Override
    public void add(Job value) {
        add(value.getType(), value);
    }

    @Override
    public void remove(JobType key) {
        delete(key);
    }
}