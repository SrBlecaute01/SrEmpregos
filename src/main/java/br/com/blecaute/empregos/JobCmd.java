package br.com.blecaute.empregos;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String l, String[] args) {

        if(cmd.getName().equalsIgnoreCase("emprego")) {

            if(!(s instanceof Player)) {
                s.sendMessage("§cApenas jogadores podem executar esse comando");
                return true;
            }

            Player p = (Player)s;
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 5, 1.0F);
            SrEmpregos.getGuiManager().openJobsGui(p);
            return true;
        }
        return false;
    }
}