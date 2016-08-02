package me.aj.gm.game;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;


public abstract class MainGame extends AbstractGameState {

    public MainGame() {
        super(0, 20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        broadcast(ChatColor.YELLOW + e.getEntity().getName() + " has died!");
    }
}
