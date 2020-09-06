package br.com.blecaute.jobs.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {

    private static DecimalFormat decimal = new DecimalFormat("#,###.##",
            new DecimalFormatSymbols(new Locale("pt", "br")));

    public static String format(double value) {
        return decimal.format(value);
    }

    public static String format(int value) {
        return decimal.format(value);
    }
}