package me.aj.gm.game;

import me.aj.gm.GameManager;
import me.aj.gm.scoreboard.common.EntryBuilder;
import me.aj.gm.scoreboard.type.Entry;
import me.aj.gm.scoreboard.type.Scoreboard;
import me.aj.gm.scoreboard.type.ScoreboardHandler;
import me.aj.gm.scoreboard.type.SimpleScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.logging.Level;

public class LobbyState extends AbstractGameState {

    private final int minPlayers, maxPlayers, countdownTime;

    private int countdown;

    private boolean startedCountdown = false;

    private Location spawn;

    private Scoreboard scoreboard = SimpleScoreboard.createScoreboard();

    LobbyState(int minPlayers, int maxPlayers, int countdownTime, Location spawn) {
        super(0L, 20L);
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.countdownTime = countdownTime;
        this.countdown = countdownTime;
        this.spawn = spawn;
    }

    @Override
    public void onStateEnter() {
        initScoreboard();
        GameManager.LOGGER.log(Level.INFO, "Entering the Pregame State");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        scoreboard.addPlayer(e.getPlayer());
        e.getPlayer().setHealth(20);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().teleport(spawn);
        e.setJoinMessage(ChatColor.YELLOW + e.getPlayer().getName() + " has joined the game! ("
                + ChatColor.AQUA + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/"
                + ChatColor.AQUA + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        scoreboard.removePlayer(e.getPlayer());
        e.setQuitMessage(ChatColor.YELLOW + e.getPlayer().getName() + " has left the game! ("
                + ChatColor.AQUA + (Bukkit.getOnlinePlayers().size() - 1) + ChatColor.YELLOW + "/"
                + ChatColor.AQUA + Bukkit.getMaxPlayers() + ChatColor.YELLOW + ")");
    }

    @Override
    public void onStateExit() {
        GameManager.LOGGER.log(Level.INFO, "Exiting Pregame State");
    }

    @Override
    public void onSecond() {
        if (enoughPlayers()) {
            //Countdown has ended, start the Game
            if (startedCountdown && countdown <= 0) {
                try {
                    scoreboard.deactivate();
                    GameAPI.getGameAPI().enterState(GameAPI.getGameAPI().getMainGame().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return;
            }
            //Start the Countdown
            if (!startedCountdown) {
                startedCountdown = true;
            }
            broadcast(ChatColor.YELLOW + "Starting game in " + ChatColor.GOLD + countdown + ChatColor.YELLOW + ((countdown == 1) ? " second!" : " seconds!"));
            countdown--;
        } else {
            if (startedCountdown) {
                startedCountdown = false;
                broadcast(ChatColor.RED + "Cancelling countdown, not enough players!");
            }
            countdown = countdownTime;
        }
    }

    private void initScoreboard() {
        scoreboard.setHandler(new ScoreboardHandler() {
            @Override
            public String getTitle(Player player) {
                return ChatColor.YELLOW + "" + ChatColor.BOLD + GameManager.getGameManager().getGameName();
            }

            @Override
            public List<Entry> getEntries(Player player) {
                return new EntryBuilder()
                        .blank()
                        .next("Players: " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + "/" + maxPlayers)
                        .blank()
                        .next(startedCountdown ? "Starting in " + ChatColor.GREEN + countdown + "s" : "Waiting...")
                        .blank()
                        .next("Map: " + ChatColor.GREEN + GameManager.getGameManager().getMapName())
                        .blank()
                        .next(ChatColor.YELLOW + "Author: M-AJ").build();
            }
        }).setUpdateInterval(20L);
        scoreboard.activate();
    }

    private boolean enoughPlayers() {
        return Bukkit.getOnlinePlayers().size() >= minPlayers;
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        e.setCancelled(true);
    }
}
