package br.com.blecaute.jobs.hook;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.model.interfaces.Hook;
import br.com.blecaute.jobs.process.PlaceholderProcess;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion implements Hook {

    @Override
    public void enable() {
        SrEmpregos.getInstance().getLogger().info(getPluginName() + " encontrado... ativando implementação!");

        register();
    }

    @Override
    public boolean hasSupport() {
        return Bukkit.getPluginManager().getPlugin(getPluginName()) != null;
    }

    @Override
    public String getPluginName() {
        return "PlaceholderAPI";
    }

    @Override
    public String getIdentifier() {
        return "jobs";
    }

    @Override
    public String getAuthor() {
        return "SrBlecaute";
    }

    @Override
    public String getVersion() {
        return "1.3.1'";
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if(p == null) return null;

        return PlaceholderProcess.getValue(getIdentifier() + "_" + params, p);
    }
}