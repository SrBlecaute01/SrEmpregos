package br.com.blecaute.jobs.command;

import br.com.blecaute.jobs.inventory.JobInventory;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("emprego")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem executar esse comando");
                return true;
            }

            Player player = (Player)sender;
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 1.0F);
            JobInventory.open(player);
            return true;
        }

        return false;
    }
}