package me.aj.gm.scoreboard.type;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Represents an advanced scoreboard that can display up to 48 characters in a single entry.
 *
 * @author TigerHix
 */
public interface Scoreboard {

    /**
     * Activate the scoreboard.
     */
    void activate();

    /**
     * Add Player
     */
    void addPlayer(Player player);

    /**
     * Remove Player
     */
    void removePlayer(Player player);

    /**
     * Deactivate the scoreboard.
     */
    void deactivate();

    /**
     * Determine if the scoreboard has been already activated.
     *
     * @return activated
     */
    boolean isActivated();

    /**
     * Returns the handler for this scoreboard.
     *
     * @return handler
     */
    ScoreboardHandler getHandler();

    /**
     * Set the handler for this scoreboard.
     *
     * @param handler handler
     */
    Scoreboard setHandler(ScoreboardHandler handler);

    /**
     * Returns the update interval (default = 10L).
     *
     * @return update interval
     */
    long getUpdateInterval();

    /**
     * Set the update interval.
     *
     * @param updateInterval update interval
     * @return this
     */
    Scoreboard setUpdateInterval(long updateInterval);

    /**
     * Returns the holder of this scoreboard.
     *
     * @return holder
     */
    List<Player> getHolders();

    /**
     * Returns Bukkit scoreboard
     *
     * @return scoreboard
     */
    org.bukkit.scoreboard.Scoreboard getBukkitScoreboard();

}
