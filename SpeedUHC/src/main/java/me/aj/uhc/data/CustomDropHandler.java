package me.aj.uhc.data;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class CustomDropHandler {

    private static CustomDropHandler instance;

    //TODO Fill config and this with data
    private HashMap<DropType, List<CustomItemDrop>> drops = Maps.newHashMap();

    private CustomDropHandler() {
    }

    public static CustomDropHandler get() {
        return instance == null ? instance = new CustomDropHandler() : instance;
    }

    /*
        Drop items based on a mob kill and the mob type
     */
    public void onMobKill(Entity entity, Location l) {
        drops.get(DropType.MOB).stream().filter(item -> item.getMob() == entity.getType()).forEach(item -> {
            for (ItemStack drop : item.getDrops()) {
                entity.getWorld().dropItem(l, drop);
            }
        });
    }

    /*
        Drop items based on a block break and the block type
     */
    public void onBlockBreak(Block block) {
        drops.get(DropType.BLOCK).stream().filter(item -> item.getBlock() == block.getType()).forEach(item -> {
            for (ItemStack drop : item.getDrops()) {
                block.getWorld().dropItem(block.getLocation(), drop);
            }
        });
    }
}
