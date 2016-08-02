package me.aj.uhc.util;

import me.aj.uhc.SpeedUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class TreeCutter extends BukkitRunnable {

    private Player player;
    private Block startBlock;
    private int type;
    private byte data;
    private int searchSquareSize = 25;
    private List<String> comparisonBlockArray = new ArrayList();
    private List<Block> blocks = new ArrayList();
    private int indexed = 0;
    private boolean loop = false;

    public TreeCutter(Player cutter, Block startBlock) {
        this.player = cutter;
        this.startBlock = startBlock;
        this.type = startBlock.getTypeId();
        this.data = ((byte) (startBlock.getData() % 4));
    }

    public void runLoop(Block b1, int x1, int z1) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 2; z++) {
                    if ((x != 0) || (y != 0) || (z != 0)) {
                        Block block = b1.getWorld().getBlockAt(b1.getX() + x, b1.getY() + y, b1.getZ() + z);
                        if ((block.getTypeId() == this.type || block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2)
                                && ((block.getData() % 4 == this.data) || (block.getData() == this.startBlock.getData()))) {
                            Block b2 = b1.getRelative(x, y, z);
                            if ((b2.getX() > x1 + this.searchSquareSize) || (b2.getX() < x1 - this.searchSquareSize) || (b2.getZ() > z1 + this.searchSquareSize) || (b2.getZ() < z1 - this.searchSquareSize)) {
                                break;
                            }
                            String s = b2.getX() + ":" + b2.getY() + ":" + b2.getZ();
                            if ((!this.comparisonBlockArray.contains(s)) && ((b2.getData() % 4 == this.data) || (block.getData() != this.startBlock.getData()))) {
                                this.comparisonBlockArray.add(s);
                                this.blocks.add(b2);
                                runLoop(b2, x1, z1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void run() {
        this.blocks.add(this.startBlock);
        runLoop(this.startBlock, this.startBlock.getX(), this.startBlock.getZ());
        cutDownTree();
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void cutDownTree() {

        if (((this.player.getItemInHand() == null) || (this.player.getItemInHand().getType() == Material.AIR)) && (!updateItemInHand())) {
            return;
        }
        long speed = 0;

        new BukkitRunnable() {
            public void run() {
                if (!TreeCutter.this.loop) {
                    for (int i = 0; i < TreeCutter.this.blocks.size(); i++) {
                        TreeCutter.this.loop = true;
                        run();
                    }
                    cancel();
                    return;
                }
                if (!TreeCutter.this.player.isOnline()) {
                    cancel();
                    return;
                }
                ItemStack item = TreeCutter.this.player.getItemInHand();
                Integer speed = 0;
                if (speed == null) {
                    if (TreeCutter.this.updateItemInHand()) {
                        TreeCutter.this.cutDownTree();
                    }
                    cancel();
                    return;
                }
                for (Block block : blocks) {
                    BlockBreakEvent event = new BlockBreakEvent(block, TreeCutter.this.player);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        Location center = block.getLocation().add(0.5D, 0.5D, 0.5D);
                        for (ItemStack drop : block.getDrops()) {
                            TreeCutter.this.startBlock.getWorld().dropItem(center, drop);
                        }
                        block.setType(Material.AIR);
                        block.setData((byte) 0);
                        item.setDurability((short) (item.getDurability() + 1));
                        if (item.getType().getMaxDurability() == item.getDurability()) {
                            TreeCutter.this.player.setItemInHand(null);
                        }
                    }
                    if (TreeCutter.this.blocks.size() - 1 <= TreeCutter.this.indexed) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(SpeedUHC.get(), 0L, speed);
    }

    public boolean updateItemInHand() {
        ItemStack item = this.player.getItemInHand();
        if ((item != null) && (item.getType() != Material.AIR)) {
            return false;
        }
        for (int index = 0; index < 36; index++) {
            ItemStack stack = this.player.getInventory().getItem(index);
            if ((stack != null)) {
                this.player.setItemInHand(stack);
                this.player.getInventory().setItem(index, null);
                return true;
            }
        }
        return false;
    }
}