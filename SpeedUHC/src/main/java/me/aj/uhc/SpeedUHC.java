package me.aj.uhc;

import me.aj.gm.GameManager;
import me.aj.gm.util.LocationUtils;
import me.aj.uhc.game.UHCGame;
import me.aj.uhc.setup.ConfigureSpawnCommand;

public class SpeedUHC extends GameManager {

    private static SpeedUHC instance;

    @Override
    public String getMapName() {
        return getConfig().getString("map");
    }

    @Override
    public String getGameName() {
        return getConfig().getString("gameName");
    }

    @Override
    public void enable() {
        instance = this;
        //Register Our Commands First
        getGameRegistry().register(new ConfigureSpawnCommand());
        //Set the Main Game State
        getGameAPI().setMainGame(UHCGame.class);
        //Start pregame. Start game at 20 players countdown 5 secs
        getGameAPI().startPreGame(1, 24, 5, LocationUtils
                .stringToLocation(getConfig().getString("lobbySpawn")));
    }

    @Override
    public void disable() {
        instance = null;
    }

    public static SpeedUHC get() {
        return instance;
    }
}
