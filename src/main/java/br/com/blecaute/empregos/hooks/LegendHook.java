package br.com.blecaute.empregos.hooks;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LegendHook implements Listener {

    private boolean enable;

    public LegendHook() {
        SrEmpregos.info("§aLegendchat encontrado... ativando implementação!");
        Bukkit.getServer().getPluginManager().registerEvents(this, SrEmpregos.getInstance());
        enable = SrEmpregos.getInstance().getConfig().getBoolean("Preferencias.Chat-Tag");
    }

    @EventHandler
    public void onChat(ChatMessageEvent e) {
        if(!enable) return;
        if(!e.getTags().contains("empregos")) return;
        if(SrEmpregos.getEmployeeManager().hasJob(e.getSender().getName())) {
            JobType job  = SrEmpregos.getEmployeeManager().getEmployeeJob(e.getSender().getName());
            String tag = SrEmpregos.getJobManager().getJobTag(job);
            e.setTagValue("empregos", tag);
        }
    }
}