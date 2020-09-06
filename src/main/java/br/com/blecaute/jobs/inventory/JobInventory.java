package br.com.blecaute.jobs.inventory;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.apis.SkullAPI;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.Employee;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.enuns.JobType;
import br.com.blecaute.jobs.model.interfaces.Job;
import br.com.blecaute.jobs.model.inventory.GuiHolder;
import br.com.blecaute.jobs.model.inventory.ItemBuilder;
import br.com.blecaute.jobs.utils.ItemUtils;
import br.com.blecaute.jobs.utils.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobInventory {

    private static EmployeeManager manager;
    private static final Map<JobType, Pair<Integer, ItemStack>> ITEMS = new HashMap<>();

    private static Pair<Integer, ItemStack> dismissItem;
    private static Pair<Integer, ItemStack> questsItem;

    private static int infoSlot;
    private static String infoName;
    private static List<String> infoLore;

    public static void open(Player player) {
        Employee employee = manager.getEmployee(player.getName());
        Map<Integer, JobType> slots = new HashMap<>();
        Inventory inventory = Bukkit.createInventory(new GuiHolder(event -> {
            int slot = event.getRawSlot();
            if(questsItem.getKey() == slot) {
                if(employee == null) {
                    player.playSound(player.getLocation(), Config.SOUND_ERROR.sound, 1.0F, 1.0F);
                    player.sendMessage(Config.WITHOUT_JOB_MESSAGE.string);
                    return;
                }

                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
                QuestInventory.open(player, 1, employee);
                return;
            }

            if(dismissItem.getKey() == slot) {
                if(employee == null) {
                    player.playSound(player.getLocation(), Config.SOUND_ERROR.sound, 1.0F, 1.0F);
                    player.sendMessage(Config.WITHOUT_JOB_MESSAGE.string);
                    return;
                }

                player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
                DismissInventory.open(player);
                return;
            }

            if(!slots.containsKey(slot)) return;
            JobType type = slots.get(slot);

            if(employee != null && employee.getJob().equals(type)) return;
            if(employee != null) {
                player.playSound(player.getLocation(), Config.SOUND_ERROR.sound, 1.0F, 1.0F);
                player.sendMessage(Config.HAS_JOB_MESSAGE.string);
                return;
            }

            Job job = manager.getJob(type);
            if(!player.hasPermission(job.getPermission())) {
                player.playSound(player.getLocation(), Config.SOUND_ERROR.sound, 1.0F, 1.0F);
                player.sendMessage(Config.WITHOUT_PERMISSION_MESSAGE.string);
                return;
            }

            player.closeInventory();
            if(manager.contractPlayer(player, type)) {
                player.playSound(player.getLocation(), Config.SOUND_SUCCESS.sound, 1.0F, 1.0F);
                player.sendMessage(Config.CONTRACT_PLAYER_MESSAGE.string.replace("@job", job.getName()));

            } else {
                player.sendMessage("§cOcorreu um erro ao tentar fazer essa operação!");
            }
        }), Config.JOB_INVENTORY_SIZE.number * 9, Config.JOB_INVENTORY_NAME.string);

        JobType type = employee != null ? employee.getJob() : null;
        ITEMS.forEach((key, value) -> {
           int slot = value.getKey();
           ItemStack item = value.getValue();

           ItemBuilder builder = new ItemBuilder(item.clone());
           builder.setName(StringUtils.replaceJobPlaceholders(builder.getName(), key));
           builder.setLore(builder.getLore().stream()
                   .map(m -> StringUtils.replaceJobPlaceholders(m, key))
                   .collect(Collectors.toList()));

           if(key.equals(type)) {
               builder.addLoreLine(Config.PLACEHOLDER_JOB_SELECTED.string);
               builder.addGlow();
           } else {
               builder.addLoreLine(Config.PLACEHOLDER_SELECT_JOB.string);
           }

           inventory.setItem(slot, builder.toItemStack());
           slots.put(slot, key);
        });

        ItemBuilder builder = new ItemBuilder(SkullAPI.getByName(player.getName()));
        builder.setName(StringUtils.replaceEmployeePlaceholders(infoName, player));
        builder.setLore(infoLore.stream()
                .map(m -> StringUtils.replaceEmployeePlaceholders(m, player))
                .collect(Collectors.toList()));

        inventory.setItem(infoSlot, builder.toItemStack());
        inventory.setItem(dismissItem.getKey(), dismissItem.getValue());
        inventory.setItem(questsItem.getKey(), questsItem.getValue());

        player.openInventory(inventory);
    }

    public static void load() {
        manager = SrEmpregos.getInstance().getEmployeeManager();

        FileConfiguration config = SrEmpregos.getInstance().getConfig();
        JobType[] job = {JobType.MINER, JobType.FARMER, JobType.FISHER, JobType.LUMBERJACK, JobType.KILLER, JobType.HUNTER, JobType.DIGGER};
        String[] path = {"minerador", "fazendeiro", "pescador", "lenhador", "assassino", "cacador", "escavador"};
        for(int i = 0; i < job.length; i++) {
            ITEMS.put(job[i], loadItem(config, "inventarios." + path[i]));
        }

        dismissItem = loadItem(config, "item-sair");
        questsItem = loadItem(config, "inventarios.item-conquistas");

        infoSlot = config.getInt("inventarios.info.slot");
        infoName = config.getString("inventarios.info.nome").replace("&", "§");
        infoLore = config.getStringList("inventarios.info.lore").stream()
                .map(m -> m.replace("&", "§"))
                .collect(Collectors.toList());
    }

    private static Pair<Integer, ItemStack> loadItem(FileConfiguration config, String path) {
        int slot = config.getInt(path + ".slot");
        ItemStack item = ItemUtils.getItemWithSkullSupport(config, path);
        return Pair.of(slot, item);
    }
}