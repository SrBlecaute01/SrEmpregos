package br.com.blecaute.jobs.process;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.model.enuns.Config;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.stream.Collectors;

public class ConfigProcess {

    public static void process() {
        FileConfiguration file = SrEmpregos.getInstance().getConfig();
        for(Config config : Config.values()) {
            config.string = file.getString(config.getPath(), "").replace("&", "§");
            config.value = file.getBoolean(config.getPath(), false);
            config.number = file.getInt(config.getPath(), 0);
            config.sound = getSound(file.getString(config.getPath()));
            config.list = file.getStringList(config.getPath()).stream()
                    .map(message -> message.replace("&", "§"))
                    .collect(Collectors.toList());
        }
    }

    private static Sound getSound(String path) {
        try {
            return Sound.valueOf(path.toUpperCase());
        } catch (Exception e) {
            return Sound.NOTE_PLING;
        }
    }
}