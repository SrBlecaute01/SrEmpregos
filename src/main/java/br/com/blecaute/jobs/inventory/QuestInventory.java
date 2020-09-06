package br.com.blecaute.jobs.inventory;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.Quest;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.inventory.GuiHolder;
import br.com.blecaute.jobs.model.inventory.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestInventory {

    private static EmployeeManager manager;

    private static final ItemStack menuItem = new ItemBuilder(Material.BOOK_AND_QUILL).setName("§eMenu principal").toItemStack();
    private static final ItemStack nextArrowItem = new ItemBuilder(Material.ARROW).setName("§7Próxima página").toItemStack();
    private static final ItemStack backArrowItem = new ItemBuilder(Material.ARROW).setName("§7Voltar").toItemStack();

    public static void open(Player player, int page, Employee employee) {
        Inventory inventory = Bukkit.createInventory(new GuiHolder(event -> {
            int slot = event.getRawSlot();
            switch (slot) {
                case 48:
                    player.playSound(player.getLocation(), Sound.CLICK, 5, 1.0F);
                    open(player, page - 1, employee);
                    break;

                case 49:
                    player.playSound(player.getLocation(), Sound.CLICK, 5, 1.0F);
                    JobInventory.open(player);
                    break;

                case 50:
                    player.playSound(player.getLocation(), Sound.CLICK, 5, 1.0F);
                    open(player, page + 1, employee);
                    break;
            }
        }), 6 * 9, Config.QUEST_INVENTORY_NAME.string);

        Quest current = manager.getCurrentQuest(employee);
        List<Quest> quests = manager.getQuests(employee.getJob());

        int slot = 11;
        for(Quest quest : getPage(page, quests)) {
            ItemBuilder builder = new ItemBuilder(quest.getIcon());

            if(employee.isCompleted(quest)) {
                builder.addLoreLine(Config.QUEST_COMPLETED.string);
            } else if(quest.equals(current)){
                builder.addGlow();
                builder.addLoreLine(Config.QUEST_STARTED.string);
            } else {
                builder.addLoreLine(Config.QUEST_BLOCKED.string);
            }

            inventory.setItem(slot, builder.toItemStack());

            slot++;
            if(slot == 16 || slot == 25) {
                slot += 4;
            } else if(slot == 34) {
                break;
            }
        }

        inventory.setItem(49, menuItem);
        if(page > 1) inventory.setItem(48, backArrowItem);
        if(page * 15 - 15 > quests.size()) inventory.setItem(50, nextArrowItem);

        player.openInventory(inventory);
    }

    public static void load() {
        manager = SrEmpregos.getInstance().getEmployeeManager();
    }

    private static <T> List<T> getPage(int page, List<T> list) {
        if(list.isEmpty()) return list;
        final int first = Math.min(page * 15 - 15, list.size() - 1);
        final int end = Math.min(list.size(), first + 15);

        return list.subList(first, end);
    }
}