package br.com.blecaute.jobs.utils;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.apis.SkullAPI;
import br.com.blecaute.jobs.model.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack getItemWithSkullSupport(FileConfiguration config, String path) {
        ItemStack item;
        if(config.getBoolean( path + ".skull.ativado")) {
            item =  SkullAPI.getByUrl(config.getString(path + ".skull.url"));
        } else {
            Material material = getMaterial(config.getString(path + ".material"));
            short data = (short) config.getInt(path + ".data");
            item = new ItemStack(material);
            item.setDurability(data);
        }

        ItemBuilder builder = new ItemBuilder(item);
        builder.setName(config.getString(path + ".nome").replace("&", "§"));
        builder.setLore(config.getStringList(path + ".lore").stream()
                .map(s -> s.replace("&", "§"))
                .collect(Collectors.toList()));

        return builder.toItemStack();
    }

    public static Material getMaterial(String material) {
        try {
            return Material.valueOf(material.toUpperCase());
        } catch (Exception e) {
            SrEmpregos.getInstance().getLogger().warning("O material " + material + " não é um material válido!");
            return Material.BARRIER;
        }
    }
}