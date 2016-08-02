package me.aj.uhc.game;

import me.aj.gm.game.GameAPI;
import me.aj.uhc.SpeedUHC;
import me.aj.uhc.data.CustomDropHandler;
import me.aj.uhc.game.event.EventType;
import me.aj.uhc.util.TreeCutter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class GameListener implements Listener {

    /*
        Disable block placement in the center of the map
        TODO
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        // e.setCancelled(true);Is in center?
    }

    /*
        A PvP Handler - Enable PvP After Event
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        if (GameAPI.getGameAPI().getCurrentState() instanceof UHCGame) {
            UHCGame game = (UHCGame) GameAPI.getGameAPI().getCurrentState();
            if (game.getNextEvent() == EventType.CAGES_OPEN || game.getNextEvent() == EventType.PVP_ENABLED) {
                //Cancel Damage Since PvP isn't enabled yet
                e.setCancelled(true);
            }
        }
    }

    /*
        Handlers: Tree Breaking, Ore Mining
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        //Tree Handling
        if (e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2) {
            new TreeCutter(e.getPlayer(), e.getBlock()).runTaskAsynchronously(SpeedUHC.get());
        }
        if (e.getBlock().getType().name().endsWith("ORE")) {
            //Don't give the actual ore
            e.getBlock().breakNaturally(null);
        }
        //Drop Handler
        CustomDropHandler.get().onBlockBreak(e.getBlock());

    }

    /*
        Handlers: Upgraded Weapons, Custom Recipes
        TODO
     */
    @EventHandler
    public void onItemCraft(CraftItemEvent e) {

    }

    /*
        Handlers: Mob Items
     */
    @EventHandler
    public void onMobKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        //Drop Handler
        e.getDrops().clear();
        CustomDropHandler.get().onMobKill(e.getEntity(), e.getEntity().getKiller().getLocation());
    }
}
