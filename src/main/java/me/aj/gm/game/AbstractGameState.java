package me.aj.gm.game;

import me.aj.gm.GameManager;
import me.aj.gm.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractGameState extends BukkitRunnable implements Listener {

    public AbstractGameState(long delay, long period) {
        this.runTaskTimer(GameManager.getGameManager(), delay, period);
    }

    @Override
    public void run() {
        onSecond();
    }

    public abstract void onSecond();

    public abstract void onStateEnter();

    public abstract void onStateExit();

    public void broadcast(String s) {
        Bukkit.broadcastMessage(ColorUtil.replace(s));
    }
}
