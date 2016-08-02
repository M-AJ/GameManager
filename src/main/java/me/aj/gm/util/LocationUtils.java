package me.aj.gm.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class LocationUtils {

    public static Chunk stringToChunk(String string) {
        String[] splits = string.split(";");
        return Bukkit.getWorld(splits[0]).getChunkAt(Integer.valueOf(splits[1]), Integer.valueOf(splits[2]));
    }

    public static String chunkToString(Chunk chunk) {
        return chunk.getWorld().getName() + ";" + chunk.getX() + ";" + chunk.getZ();
    }

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static Location stringToLocation(String str) {
        String[] strar = str.split(";");
        Location newLoc = new Location(Bukkit.getWorld(strar[0]), Double.valueOf(strar[1]).doubleValue(), Double.valueOf(strar[2]).doubleValue(), Double.valueOf(strar[3]).doubleValue(), Float.valueOf(strar[4]).floatValue(), Float.valueOf(strar[5]).floatValue());
        return newLoc.clone();
    }

    public static List<Location> getBlocks(final Location l, final double radius) {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int YCoord = (int) l.getY();
        List<Location> tempList = Lists.newArrayList();
        for (int x = (int) -radius; x <= radius; x++) {
            for (int z = (int) -radius; z <= radius; z++) {
                for (int y = (int) -radius; y <= radius; y++) {
                    tempList.add(new Location(w, xCoord + x, YCoord + y, zCoord + z));
                }
            }
        }
        return tempList;
    }
}
