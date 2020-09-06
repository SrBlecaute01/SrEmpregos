package br.com.blecaute.jobs.process;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.dao.JobDao;
import br.com.blecaute.jobs.model.Quest;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.blecaute.jobs.utils.FileUtils;
import br.com.blecaute.jobs.utils.ItemUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobProcess {

    public static void process() {
        JobDao jobDao = JobDao.getInstance();
        jobDao.clear();

        for(JobType type : JobType.values()) {
            FileUtils.createConfig(type.getFile(), "empregos");
            FileConfiguration config = FileUtils.getConfiguration(FileUtils.getFile(type.getFile(), "empregos"));

            String name = config.getString("nome").replace("&", "§");
            String tag = config.getString("tag").replace("&", "§");
            BigDecimal salary = new BigDecimal("" + config.getDouble("salario"));
            int meta = config.getInt("meta");

            List<String> worlds = config.getStringList("mundos").stream().map(String::toLowerCase).collect(Collectors.toList());
            List<String> values = config.getStringList("validos").stream().map(String::toUpperCase).collect(Collectors.toList());
            List<Quest> quests = new ArrayList<>();
            for(String section : config.getConfigurationSection("desafios").getKeys(false)) {
                String path = "desafios." + section;

                int position = config.getInt(path + ".ordem");
                int questMeta = config.getInt(path + ".meta");
                boolean increaseSalary = config.getBoolean(path + ".aumento.ativado");
                BigDecimal questSalary = new BigDecimal("" + config.getDouble(path + ".aumento.valor"));

                ItemStack icon = ItemUtils.getItemWithSkullSupport(config, path + ".icone");
                List<String> cmd = config.getStringList(path + ".comandos");
                List<String> msg = config.getStringList(path + ".mensagem").stream()
                        .map(m -> m.replace("&", "§"))
                        .collect(Collectors.toList());

                quests.add(new Quest(section, type, position, icon, questSalary, questMeta, increaseSalary, msg, cmd));
            }

            try {
                Class<? extends Job> clazz = type.getJobClass();
                Constructor<? extends Job> jobConstructor = clazz.getConstructor(String.class, String.class,
                        BigDecimal.class, int.class, List.class, List.class, List.class);

                Job job = jobConstructor.newInstance(name, tag, salary, meta, worlds, quests, values);
                jobDao.add(job);

            } catch (Exception e) {
                SrEmpregos.getInstance().getLogger().warning("Ocorreu um erro ao tentar registrar um emprego: " + e.getMessage());
            }
        }
    }
}