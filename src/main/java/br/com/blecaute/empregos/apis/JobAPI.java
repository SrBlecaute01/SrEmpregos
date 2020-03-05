package br.com.blecaute.empregos.apis;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Quest;

import java.math.BigDecimal;
import java.util.List;

public class JobAPI {

    private JobAPI() {}

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna o nome de um emprego
     */

    public static String getJobName(JobType job) {
        return SrEmpregos.getJobManager().getJobName(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna a tag de um emprego
     */

    public static String getJobTag(JobType job) {
        return SrEmpregos.getJobManager().getJobTag(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna o salário inicial do emprego
     */

    public static BigDecimal getJobSalary(JobType job) {
        return SrEmpregos.getJobManager().getJobSalary(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna a meta a ser concluida para receber o salário
     */

    public static Integer getJobMeta(JobType job) {
        return SrEmpregos.getJobManager().getJobMeta(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna a lista de mundos válidos para esse emprego
     */

    public static List<String> getJobWorlds(JobType job) {
        return SrEmpregos.getJobManager().getJobWorlds(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @return retorna a lista de quests do emprego
     */

    public static List<Quest> getJobQuests(JobType job) {
        return SrEmpregos.getJobManager().getJobQuests(job);
    }

    /**
     *
     * @param job emprego a ser verificado
     * @param world mundo a ser verificado
     * @return verifica se é um mundo válido
     */

    public static Boolean isValidJobWorld(JobType job, String world) {
        return SrEmpregos.getJobManager().isValidJobWorld(job, world);
    }
}