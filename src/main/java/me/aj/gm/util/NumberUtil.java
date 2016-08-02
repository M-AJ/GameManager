package me.aj.gm.util;

public class NumberUtil {

    public static String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}
