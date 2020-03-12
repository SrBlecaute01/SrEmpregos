package br.com.blecaute.empregos.manager;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.jobs.Job;
import br.com.blecaute.empregos.objects.Quest;
import br.com.blecaute.empregos.utils.FileUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JobManager {

    private HashMap<JobType, List<Material>> validWMaterials = new HashMap<>();
    private HashMap<JobType, List<EntityType>> validEntity = new HashMap<>();

    public JobManager() {
        List<Material> miner = checkMaterials(FileUtils.getConfiguration(FileUtils.getFile("minerador", "empregos")));
        List<Material> digger = checkMaterials(FileUtils.getConfiguration(FileUtils.getFile("escavador", "empregos")));
        List<Material> lumber = checkMaterials(FileUtils.getConfiguration(FileUtils.getFile("lenhador", "empregos")));
        List<Material> farmer = checkMaterials(FileUtils.getConfiguration(FileUtils.getFile("fazendeiro", "empregos")));
        List<Material> fisher = checkMaterials(FileUtils.getConfiguration(FileUtils.getFile("pescador", "empregos")));

        validWMaterials.put(JobType.MINER, miner);
        validWMaterials.put(JobType.DIGGER, digger);
        validWMaterials.put(JobType.LUMBERJACK, lumber);
        validWMaterials.put(JobType.FARMER, farmer);
        validWMaterials.put(JobType.FISHER, fisher);

        List<EntityType> killer = checkEntity(FileUtils.getConfiguration(FileUtils.getFile("assassino", "empregos")));
        List<EntityType> hunter = checkEntity(FileUtils.getConfiguration(FileUtils.getFile("cacador", "empregos")));

        validEntity.put(JobType.KILLER, killer);
        validEntity.put(JobType.HUNTER, hunter);
    }

    public String getJobName(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getName).findFirst().orElse("invalid");
    }

    public String getJobTag(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getTag).findFirst().orElse("");
    }

    public BigDecimal getJobSalary(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getSalary).findFirst().orElse(new BigDecimal("0"));
    }

    public Integer getJobMeta(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getMeta).findFirst().orElse(0);
    }

    public List<String> getJobWorlds(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getWorlds).findFirst().orElse(new ArrayList<>());
    }

    public List<Quest> getJobQuests(JobType job) {
        return SrEmpregos.getInstance().getJobs().stream().filter(f -> f.getType().equals(job)).map(Job::getQuests).findFirst().orElse(new ArrayList<>());
    }

    public Boolean isValidJobWorld(JobType job, String world) {
        return this.getJobWorlds(job).stream().anyMatch(w -> w.equalsIgnoreCase(world));
    }

    public Boolean isValidJobMaterial(JobType job, Material m) {
        return validWMaterials.containsKey(job) && validWMaterials.get(job).contains(m);
    }

    public Boolean isValidJobEntity(JobType job, EntityType entity) {
        return validEntity.containsKey(job) && validEntity.get(job).contains(entity);
    }

    private List<Material> checkMaterials(FileConfiguration config) {
        return config.getStringList("Validos").stream().filter(f -> {

            try {
                Material.valueOf(f.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                SrEmpregos.info("§cO material " + f + " não é um material válido!");
                return false;
            }

        }).map(e -> Material.valueOf(e.toUpperCase())).collect(Collectors.toList());
    }

    private List<EntityType> checkEntity(FileConfiguration config) {
        return config.getStringList("Validos").stream().filter(f -> {

            try {
                EntityType.valueOf(f);
                return true;
            } catch (IllegalArgumentException e) {
                SrEmpregos.info("§cA entidade " + f + " não é uma entidade válida!");
                return false;
            }

        }).map(m -> EntityType.valueOf(m.toUpperCase())).collect(Collectors.toList());
    }
}