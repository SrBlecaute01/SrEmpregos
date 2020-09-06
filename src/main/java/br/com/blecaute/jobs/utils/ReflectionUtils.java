package br.com.blecaute.jobs.utils;

import org.bukkit.Bukkit;

/**
 * @author Mior
 * @version 1.0
 */
public class ReflectionUtils {

	private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

	public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server." + version + "." + name);
	}

	public static Class<?> getOBClass(String name) throws ClassNotFoundException {
		return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
	}
}