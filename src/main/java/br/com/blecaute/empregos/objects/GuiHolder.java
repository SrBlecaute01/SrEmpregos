package br.com.blecaute.empregos.objects;

import br.com.blecaute.empregos.enums.GuiType;
import br.com.blecaute.empregos.enums.JobType;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    @Getter private GuiType type;
    @Getter private int page;
    @Getter private JobType job;

    public GuiHolder(GuiType type) {
        this.type = type;
    }

    public GuiHolder(GuiType type, int page, JobType job) {
        this.type = type;
        this.page = page;
        this.job = job;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
