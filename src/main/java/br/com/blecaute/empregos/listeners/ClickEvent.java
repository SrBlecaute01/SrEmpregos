package br.com.blecaute.empregos.listeners;

import br.com.blecaute.empregos.SrEmpregos;
import br.com.blecaute.empregos.enums.JobType;
import br.com.blecaute.empregos.objects.GuiHolder;
import br.com.blecaute.empregos.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getInventory() == null) return;
        if(!(e.getInventory().getHolder() instanceof GuiHolder)) return;
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        e.setCancelled(true);

        Player p = (Player)e.getWhoClicked();
        GuiHolder holder = (GuiHolder) e.getInventory().getHolder();

        switch (holder.getType()) {

            case JOBS:

                if(e.getRawSlot() == 37) {

                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);
                    if(!SrEmpregos.getEmployeeManager().hasJob(p.getName())) {
                        Utils.sendSound(p, SrEmpregos.getInstance().getConfig().getString("Sons.Erro"));
                        p.sendMessage(SrEmpregos.getMessagesManager().getMessage("sem.emprego"));
                        break;
                    }

                    JobType job = SrEmpregos.getEmployeeManager().getEmployeeJob(p.getName());
                    SrEmpregos.getGuiManager().openQuests(p, 1, job);
                    break;
                }

                if(e.getRawSlot() == 44) {

                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);
                    if(!SrEmpregos.getEmployeeManager().hasJob(p.getName())) {
                        Utils.sendSound(p, SrEmpregos.getInstance().getConfig().getString("Sons.Erro"));
                        p.sendMessage(SrEmpregos.getMessagesManager().getMessage("sem.emprego"));
                        break;
                    }

                    SrEmpregos.getGuiManager().openDismiss(p);
                    break;
                }

                JobType job = null;
                switch (e.getRawSlot()) {

                    case 10:
                        job = JobType.MINER;
                        break;

                    case 12:
                        job = JobType.LUMBERJACK;
                        break;

                    case 14:
                        job = JobType.DIGGER;
                        break;

                    case 16:
                        job = JobType.HUNTER;
                        break;

                    case 20:
                        job = JobType.FISHER;
                        break;

                    case 22:
                        job = JobType.FARMER;
                        break;

                    case 24:
                        job = JobType.KILLER;
                        break;

                    default:
                        break;
                }

                if(job == null) break;
                p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);

                if(SrEmpregos.getEmployeeManager().isInJob(p.getName(), job)) break;
                if(SrEmpregos.getEmployeeManager().hasJob(p.getName())) {
                    Utils.sendSound(p, SrEmpregos.getInstance().getConfig().getString("Sons.Erro"));
                    p.sendMessage(SrEmpregos.getMessagesManager().getMessage("com.emprego"));
                    break;
                }

                p.closeInventory();
                if(SrEmpregos.getEmployeeManager().contractPlayer(p, job)) {
                    Utils.sendSound(p, SrEmpregos.getInstance().getConfig().getString("Sons.Sucesso"));
                    p.sendMessage(SrEmpregos.getMessagesManager().getMessage("contratado").replace("@emprego", SrEmpregos.getJobManager().getJobName(job)));
                } else {
                    p.sendMessage("§cOcorreu um erro ao tentar fazer essa operação!");
                }

                break;

            case CONFIRM:

                if(e.getRawSlot() == 11) {
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.VILLAGER_HAGGLE, 5, 1.0F);
                    break;
                }

                if(e.getRawSlot() == 15) {
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);

                    if(SrEmpregos.getEmployeeManager().dismissPlayer(p.getName())) {
                        Utils.sendSound(p, SrEmpregos.getInstance().getConfig().getString("Sons.Sucesso"));
                        p.sendMessage(SrEmpregos.getMessagesManager().getMessage("demissao"));
                    } else {
                        p.sendMessage("§cOcorreu um erro ao tentar fazer essa operação!");
                    }
                }

                break;

            case QUESTS:

                if(e.getRawSlot() == 48) {
                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);
                    int page = holder.getPage() - 1;
                    SrEmpregos.getGuiManager().openQuests(p, page, holder.getJob());
                    break;
                }

                if(e.getRawSlot() == 49) {
                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);
                    SrEmpregos.getGuiManager().openJobsGui(p);
                    break;
                }

                if(e.getRawSlot() == 50) {
                    p.playSound(p.getLocation(), Sound.CLICK, 5, 1.0F);
                    int page = holder.getPage() + 1;
                    SrEmpregos.getGuiManager().openQuests(p, page, holder.getJob());
                    break;
                }

                break;

            default:
                break;
        }
    }
}
