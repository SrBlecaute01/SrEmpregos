package br.com.blecaute.empregos.enums;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.jobs.Job;

public enum JobType {

    HUNTER,
    FARMER,
    KILLER,
    LUMBERJACK,
    MINER,
    DIGGER,
    FISHER;

    public static String getName(String job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().name().equalsIgnoreCase(job)).map(Job::getName).findFirst().orElse("");
    }
}