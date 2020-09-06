package br.com.blecaute.jobs.model;

import br.com.blecaute.jobs.model.enuns.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor @Data
public class Quest {

    private final String id;
    private final JobType job;
    private int position;
    private ItemStack icon;

    private BigDecimal salary;
    private int meta;
    private boolean increaseSalary;
    private List<String> messageComplete;
    private List<String> commands;

    public ItemStack getIcon() {
        return this.icon.clone();
    }
}