package me.aj.gm;

import me.aj.gm.game.GameAPI;
import me.aj.gm.player.GamePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class GameManager extends JavaPlugin {

    public abstract String getMapName();

    public abstract String getGameName();

    public static Logger LOGGER = Logger.getLogger("GameManager");

    private static GameManager instance;

    private GameRegistry gameRegistry;

    @Override
    public void onEnable() {
        instance = this;
        gameRegistry = new GameRegistry();
        saveDefaultConfig();
        enable();
    }

    public abstract void enable();

    @Override
    public void onDisable() {
        disable();
        instance = null;
    }

    public abstract void disable();

    public static GameManager getGameManager() {
        return instance;
    }

    public GameRegistry getGameRegistry() {
        return gameRegistry;
    }

    public GameAPI getGameAPI() {
        return GameAPI.getGameAPI();
    }

    public GamePlayer getGamePlayer(String name) {
        return GamePlayer.getPlayer(name);
    }
}
