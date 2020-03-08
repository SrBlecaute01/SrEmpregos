package br.com.blecaute.empregos.objects;

import br.com.blecaute.empregos.enums.GuiType;
import br.com.blecaute.empregos.enums.JobType;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class GuiHolder implements InventoryHolder {

    private GuiType type;
    private int page;
    private JobType job;

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
