package me.aj.gm.player;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class GamePlayer {

    private static ConcurrentHashMap<String, GamePlayer> players = new ConcurrentHashMap<>();

    public static GamePlayer getPlayer(String name) {
        return players.get(name) == null ? new GamePlayer(name) : players.get(name);
    }

    public static GamePlayer getPlayer(Player p) {
        return getPlayer(p.getName());
    }

    private int kills, assists;

    private String name;

    private GamePlayer(String name) {
        this.name = name;
        players.put(name, this);
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }

    public String getName() {
        return name;
    }
}
