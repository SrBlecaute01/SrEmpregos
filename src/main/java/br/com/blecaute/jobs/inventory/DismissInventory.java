package br.com.blecaute.jobs.inventory;

import br.com.blecaute.jobs.SrEmpregos;
import br.com.blecaute.jobs.manager.EmployeeManager;
import br.com.blecaute.jobs.model.enuns.Config;
import br.com.blecaute.jobs.model.inventory.GuiHolder;
import br.com.blecaute.jobs.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DismissInventory {

    private static ItemStack confirmItem;
    private static ItemStack cancelItem;
    private static ItemStack quitItem;

    private static EmployeeManager manager;

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new GuiHolder(event -> {
            int slot = event.getSlot();
            switch (slot) {
                case 11:
                    player.playSound(player.getLocation(), Sound.VILLAGER_HAGGLE, 1.0F, 1.0F);
                    JobInventory.open(player);
                    break;

                case 15:
                    player.closeInventory();
                    if(manager.dismissPlayer(player.getName())) {
                        player.playSound(player.getLocation(), Config.SOUND_SUCCESS.sound, 1.0F, 1.0F);
                        player.sendMessage(Config.DISMISS_MESSAGE.string);
                    } else {
                        player.sendMessage("§cOcorreu um erro ao tentar fazer essa operação!");
                    }
                    break;
            }
        }), 3 * 9, Config.DISMISS_INVENTORY_NAME.string);

        inventory.setItem(11, cancelItem);
        inventory.setItem(13, quitItem);
        inventory.setItem(15, confirmItem);

        player.openInventory(inventory);
    }

    public static void load() {
        manager = SrEmpregos.getInstance().getEmployeeManager();

        FileConfiguration config = SrEmpregos.getInstance().getConfig();
        confirmItem = ItemUtils.getItemWithSkullSupport(config, "item-confirmar");
        cancelItem = ItemUtils.getItemWithSkullSupport(config, "item-cancelar");
        quitItem = ItemUtils.getItemWithSkullSupport(config, "item-sair");
    }
}