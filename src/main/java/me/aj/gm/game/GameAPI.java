package me.aj.gm.game;

import me.aj.gm.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GameAPI {

    private LobbyState lobbyState;

    private Class<? extends MainGame> mainGame;

    private AbstractGameState currentState;

    private static GameAPI instance;

    private GameAPI() {
    }

    public static GameAPI getGameAPI() {
        return instance == null ? instance = new GameAPI() : instance;
    }

    public void startPreGame(int minplayers, int maxplayers, int countdown, Location spawn) {
        lobbyState = new LobbyState(minplayers, maxplayers, countdown, spawn);
        GameManager.getGameManager().getGameRegistry().register(lobbyState);
        currentState = lobbyState;
        lobbyState.onStateEnter();
    }

    public void setMainGame(Class<? extends MainGame> mainGameclazz) {
        this.mainGame = mainGameclazz;
    }

    public void enterState(AbstractGameState enterState) {
        GameManager.getGameManager().getGameRegistry().unregister(currentState);
        Bukkit.getScheduler().cancelTask(currentState.getTaskId());
        currentState.onStateExit();

        this.currentState = enterState;
        GameManager.getGameManager().getGameRegistry().register(enterState);
        enterState.onStateEnter();

    }

    public AbstractGameState getCurrentState() {
        return currentState;
    }

    public Class<? extends MainGame> getMainGame() {
        return mainGame;
    }
}
