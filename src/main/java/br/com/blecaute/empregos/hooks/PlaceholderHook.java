package br.com.blecaute.empregos.hooks;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {

    private String withoutJob;
    private String bar;
    private int amount;
    private String colorCompleted;
    private String colorIncomplete;
    String first;
    String end;

    public PlaceholderHook() {
        SrEmpregos.info("§aPlaceholderAPI encontrado... ativando implementação!");

        withoutJob = SrEmpregos.getInstance().getConfig().getString("Preferencias.Placeholder.Desempregado");
        bar = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Barra");
        amount = SrEmpregos.getInstance().getConfig().getInt("Preferencias.Barra-De-Progress.Quantidade");
        colorCompleted = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Cor-Completa").replace("&", "§");
        colorIncomplete =  SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Cor-Imcompleta").replace("&", "§");
        first = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Primeiro").replace("&", "§");
        end = SrEmpregos.getInstance().getConfig().getString("Preferencias.Barra-De-Progress.Ultimo").replace("&", "§");
    }

    @Override
    public String getIdentifier() {
        return "empregos";
    }

    @Override
    public String getAuthor() {
        return "SrBlecaute";
    }

    @Override
    public String getVersion() {
        return "1.1";
    }

    @Override
    public String onRequest(OfflinePlayer p, String param) {
        if(p == null) return null;

        switch (param) {
            case "player_emprego":

                if (SrEmpregos.getInstance().getEmployeeManager().hasJob(p.getName())) {
                    JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
                    return SrEmpregos.getInstance().getJobManager().getJobTag(job);

                } else {
                    return withoutJob.replace("&", "§");
                }

            case "player_meta_total":
                return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeMeta(p.getName());

            case "player_current":
                return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());

            case "player_quests":
                return "" + SrEmpregos.getInstance().getEmployeeManager().getEmployeeQuests(p.getName()).size();

            case "player_salary":
                return Utils.getNumberFormatted(SrEmpregos.getInstance().getEmployeeManager().getEmployeeSalary(p.getName()).doubleValue());

            case "player_progress": {

                if (SrEmpregos.getInstance().getEmployeeManager().hasJob(p.getName())) {
                    int current = SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());
                    JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
                    int meta = SrEmpregos.getInstance().getJobManager().getJobMeta(job);
                    return "" + (float) ((current * 100) / meta);
                } else {
                    return "" + 0.0;
                 }
            }

            case "player_progress_bar": {
                int current = SrEmpregos.getInstance().getEmployeeManager().getEmployeeCurrentMeta(p.getName());
                JobType job = SrEmpregos.getInstance().getEmployeeManager().getEmployeeJob(p.getName());
                int meta = SrEmpregos.getInstance().getJobManager().getJobMeta(job);
                if (meta <= 0) meta = 100;

                return first + Utils.getProgressBar(current, meta, bar, amount, colorCompleted, colorIncomplete) + end;
            }

            default:
                break;
        }

        return null;
    }
}
