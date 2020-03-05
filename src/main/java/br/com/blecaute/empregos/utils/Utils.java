package br.com.blecaute.empregos.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class Utils {

    private static DecimalFormat decimal = new DecimalFormat("#.##", new DecimalFormatSymbols(new Locale("pt", "br")));

    public static void sendSound(Player p, String sound) {
        Arrays.stream(Sound.values()).filter(f -> f.name().equalsIgnoreCase(sound)).forEach(e -> p.playSound(p.getLocation(), e, 5, 1.0F));
    }

    public static String getNumberFormatted(double number) {
        return decimal.format(number);
    }

    public static String getProgressBar(int current, int total, String bar, int barAmount, String barCompletedColor, String barIncompleteColor) {
        float percent = (float) current / total;
        if(percent > 1) percent = 1;
        int bars1 = Math.round(barAmount * percent);
        int bars2 = barAmount - bars1;
        return barCompletedColor + StringUtils.repeat(bar, bars1) + barIncompleteColor + StringUtils.repeat(bar, bars2);
    }
}