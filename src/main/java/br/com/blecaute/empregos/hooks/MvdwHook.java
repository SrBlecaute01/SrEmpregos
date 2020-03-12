package br.com.blecaute.empregos.hooks;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.utils.Utils;
import org.bukkit.OfflinePlayer;

public class MvdwHook {

    private String withoutJob;
    private String bar;
    private int amount;
    private String colorCompleted;
    private String colorIncomplete;
    String first;
    String end;

    public MvdwHook() {
        SrEmpregos.info("§aMVdWPlaceholderAPI encontrado... ativando implementação!");

        withoutJob = SrEmpregos.getInstance().getConfig().getString("Preferencias.Placeholder.Desempregado");
        bar = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Barra");
        amount = SrEmpregos.getInstance().getConfig().getInt("Preferencias.Barra-De-Progress.Quantidade");
        colorCompleted = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Cor-Completa").replace("&", "§");
        colorIncomplete =  SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Cor-Imcompleta").replace("&", "§");
        first = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Primeiro").replace("&", "§");
        end = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Ultimo").replace("&", "§");

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_emprego", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();

            if (SrEmpregos.getInstance().getEmployeeManager().hasJob(p.getName())) {
                JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
                return SrEmpregos.getInstance().getJobManager().getJobTag(job);

            } else {
                return withoutJob.replace("&", "§");
            }
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_meta_total", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();
            return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeMeta(p.getName());
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_current", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();
            return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_quests", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();
            return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeQuests(p.getName()).size();
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_salary", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();
            return Utils.getNumberFormatted(SrEmpregos.getInstance().getEmployeeManager().getEmployeeSalary(p.getName()).doubleValue());
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_progress", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();

            if (SrEmpregos.getInstance().getEmployeeManager().hasJob(p.getName())) {
                int current = SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());
                JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
                int meta = SrEmpregos.getInstance().getJobManager().getJobMeta(job);
                return "" + (float) ((current * 100) / meta);
            } else {
                return "" + 0.0;
            }
        });

        PlaceholderAPI.registerPlaceholder(SrEmpregos.getInstance(), "empregos_player_progress_bar", placeholderReplaceEvent -> {
            if(placeholderReplaceEvent.getOfflinePlayer() == null) return null;
            OfflinePlayer p = placeholderReplaceEvent.getOfflinePlayer();

            int current = SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());
            JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
            int meta = SrEmpregos.getInstance().getJobManager().getJobMeta(job);
            if (meta <= 0) meta = 100;

            return first + Utils.getProgressBar(current, meta, bar, amount, colorCompleted, colorIncomplete) + end;
        });
    }
}