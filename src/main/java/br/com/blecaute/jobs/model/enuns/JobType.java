package br.com.blecaute.jobs.model.enuns;

import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.blecaute.jobs.model.jobs.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JobType {

    HUNTER("cacador.yml", Hunter.class),
    FARMER("fazendeiro.yml", Farmer.class),
    KILLER("assassino.yml", Killer.class),
    LUMBERJACK("lenhador.yml", Lumberjack.class),
    MINER("minerador.yml", Miner.class),
    DIGGER("escavador.yml", Digger.class),
    FISHER("pescador.yml", Fisher.class);

    @Getter private final String file;
    @Getter private final Class<? extends Job> jobClass;

    private final JobDao JOB_DAO = JobDao.getInstance();

    public String getName() {
        return JOB_DAO.has(this) ? JOB_DAO.get(this).getName() : null;
    }
}