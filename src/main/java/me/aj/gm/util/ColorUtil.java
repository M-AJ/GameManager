package me.aj.gm.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String replace(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] replace(String[] message) {
        String[] returnVal = new String[message.length];
        for (int x = 0; x < message.length; x++) {
            returnVal[x] = replace(message[x]);
        }
        return returnVal;
    }
}
