package br.com.blecaute.empregos.utils;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.apis.events.JobGetPaymentEvent;
import br.com.blecaute.empregos.apis.events.JobQuestCompleteEvent;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.Employee;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobUtils {

    @SuppressWarnings("deprecation")
    public static void executeEvents(Player p, Employee employee, JobType job) {

        SrEmpregos.getJobManager().getJobQuests(job).stream().filter(f -> !SrEmpregos.getEmployeeManager().isCompleted(p.getName(), f)).forEach(q -> {
            if(employee.getCompletedMeta() >= q.getMeta()) {
                JobQuestCompleteEvent event = new JobQuestCompleteEvent(p, q, employee);
                Bukkit.getPluginManager().callEvent(event);

                if(!event.isCancelled()) {

                    if(event.getQuest().getIncreaseSalary()) {
                        employee.addSalary(event.getQuest().getSalary());
                    }

                    if(event.getQuest().getMessageComplete() != null) {
                        event.getQuest().getMessageComplete().stream().map(m -> m.replace("@player", p.getName()))
                                .forEach(p::sendMessage);
                    }

                    if(event.getQuest().getCommands() != null) {
                        event.getQuest().getCommands().stream()
                                .map(m -> m.replace("@player", p.getName()))
                                .forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd));
                    }

                    employee.getQuestsComplete().add(q.getId());
                }
            }
        });

        int jobMeta = SrEmpregos.getJobManager().getJobMeta(job);
        if(employee.getCurrentMeta() >= jobMeta) {
            JobGetPaymentEvent event = new JobGetPaymentEvent(p, employee, job);
            Bukkit.getPluginManager().callEvent(event);
            SrEmpregos.getEconomy().depositPlayer(p.getName(), event.getEmployee().getSalary().doubleValue());
            employee.setCurrentMeta(0);

            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 5, 1.0F);
            SrEmpregos.getMessagesManager().getMessageList("pagamento").stream()
                    .map(m -> m.replace("@salario", Utils.getNumberFormatted(event.getEmployee().getSalary().doubleValue())))
                    .forEach(p::sendMessage);
        }
    }

    public static String serializeQuests(List<String> quests) {
        if(quests.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < quests.size(); i++) {
            sb.append(quests.get(i));
            if(i + 1 < quests.size()) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static List<String> deserializeQuests(String serialized) {
        List<String> list = new ArrayList<>();
        if(serialized.equals("[]")) return list;
        Collections.addAll(list, serialized.split(";"));
        return list;
    }

    public static boolean isGrown(Block block) {
        MaterialData m = block.getState().getData();
        if(m instanceof Crops) {
            Crops crops = (Crops) m;
            return crops.getState().equals(CropState.RIPE);
        }
        return false;
    }
}