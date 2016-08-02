package me.aj.uhc.game.border;

import me.aj.uhc.SpeedUHC;
import me.aj.uhc.game.UHCGame;
import me.aj.uhc.game.event.EventType;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderManager extends BukkitRunnable {

    private WorldBorder worldBorder;

    private int radius, maxRadius;

    private static boolean created = false;

    private UHCGame game;

    public BorderManager(UHCGame game) {
        if (created) return;
        this.game = game;
        this.worldBorder = game.getArena().getWorld().getWorldBorder();
        this.radius = SpeedUHC.get().getConfig().getInt("borderRadius");
        this.maxRadius = SpeedUHC.get().getConfig().getInt("maxRadius");
        worldBorder.setCenter(game.getArena().getCenter());
        worldBorder.setSize(radius);
        created = true;
    }

    public WorldBorder getWorldBorder() {
        return worldBorder;
    }

    private void update() {
        worldBorder.setSize(radius);
    }

    @Override
    public void run() {
        if (game.getNextEvent() == EventType.SECOND_STAGE) {
            //Border is Shrinking Now
            if (radius <= maxRadius) {
                //We reached the radius limit
                cancel();
                return;
            }
            radius--;
            update();
        } else if (game.getNextEvent() == EventType.NETHER_CLOSE) {
            //In the Second Stage Now, Cancel this task
            cancel();
        }
    }
}
