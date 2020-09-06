package br.com.blecaute.jobs.utils;

import br.com.blecaute.jobs.SrEmpregos;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtils {

	public static void createConfig(String file, String folder) {
		if (!new File(SrEmpregos.getInstance().getDataFolder() + File.separator + folder, file).exists()) {
			SrEmpregos.getInstance().saveResource(folder + File.separator + file, false);
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void createFolder(String folder) {
		try {
			File pasta = new File(SrEmpregos.getInstance().getDataFolder() + File.separator + folder);
			if (!pasta.exists()) {
				pasta.mkdirs();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static File getFile(String file, String folder) {
		return new File(SrEmpregos.getInstance().getDataFolder() + File.separator + folder, file);
	}

	public static FileConfiguration getConfiguration(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}
}