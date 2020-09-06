package br.com.blecaute.jobs.hook;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.model.interfaces.Hook;
import br.com.blecaute.jobs.process.PlaceholderProcess;
import org.bukkit.Bukkit;

public class MVdWPlaceholderHook implements Hook {

    @Override
    public void enable() {
        SrEmpregos.getInstance().getLogger().info(getPluginName() + " encontrado... ativando implementação!");

        PlaceholderProcess.getPlaceholders().forEach((key, value) ->
                PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), key, replacer -> {
                    if(replacer.getOfflinePlayer() == null) return null;
                    return value.apply(replacer.getOfflinePlayer());
                }));
    }

    @Override
    public boolean hasSupport() {
        return Bukkit.getPluginManager().getPlugin(getPluginName()) != null;
    }

    @Override
    public String getPluginName() {
        return "MVdWPlaceholderAPI";
    }
}