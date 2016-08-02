package me.aj.uhc.data;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomItemDrop {

    private DropType dropType;

    private List<ItemStack> drops = Lists.newArrayList();

    private Material block;
    private EntityType mob;

    public CustomItemDrop(DropType dropType, List<ItemStack> drops, Material block, EntityType mob) {
        this.dropType = dropType;
        this.drops = drops;
        this.block = block;
        this.mob = mob;
    }

    public DropType getDropType() {
        return dropType;
    }

    public EntityType getMob() {
        return mob;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public Material getBlock() {
        return block;
    }
}
