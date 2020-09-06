package br.com.blecaute.jobs.listeners;

import br.com.blecaute.jobs.model.inventory.GuiHolder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getInventory() == null
                || !(event.getInventory().getHolder() instanceof GuiHolder)) return;

        event.setCancelled(true);
        if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            GuiHolder holder = (GuiHolder) event.getInventory().getHolder();
            holder.getConsumer().accept(event);
        }
    }
}