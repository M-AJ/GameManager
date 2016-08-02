package me.aj.uhc.game;

import me.aj.gm.GameManager;
import me.aj.gm.chat.Title;
import me.aj.gm.game.MainGame;
import me.aj.gm.player.GamePlayer;
import me.aj.gm.scoreboard.common.EntryBuilder;
import me.aj.gm.scoreboard.type.Entry;
import me.aj.gm.scoreboard.type.Scoreboard;
import me.aj.gm.scoreboard.type.ScoreboardHandler;
import me.aj.gm.scoreboard.type.SimpleScoreboard;
import me.aj.gm.util.NumberUtil;
import me.aj.uhc.SpeedUHC;
import me.aj.uhc.arena.Arena;
import me.aj.uhc.game.border.BorderManager;
import me.aj.uhc.game.event.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class UHCGame extends MainGame {

    private final Arena arena = new Arena();
    private final BorderManager borderManager = new BorderManager(this);

    private final CagesOpenEvent cagesOpenEvent = new CagesOpenEvent();
    private final PvPEnabledEvent pvpEnabledEvent = new PvPEnabledEvent();
    private final BorderShrinkEvent borderShrinkEvent = new BorderShrinkEvent();
    private final SecondStageEvent secondStageEvent = new SecondStageEvent();

    private EventType nextEvent = EventType.CAGES_OPEN;

    private Title title = new Title(ChatColor.YELLOW + "Speed UHC", ChatColor.GREEN + "Solo Normal Mode");

    @Override
    public void onSecond() {
        switch (nextEvent) {
            case CAGES_OPEN:
                broadcast(ChatColor.YELLOW + "Cages open in: " + ChatColor.RED +
                        cagesOpenEvent.getRemainingSeconds() + ChatColor.YELLOW + (cagesOpenEvent.getRemainingSeconds() == 1 ? " second" : " seconds") + "!");
                cagesOpenEvent.downSecond();
                //Main Title
                if (cagesOpenEvent.getRemainingSeconds() == 9) {
                    title.setFadeInTime(1);
                    title.setFadeOutTime(1);
                    title.setStayTime(1);
                    title.broadcast();
                }
                if (cagesOpenEvent.getRemainingSeconds() >= 1 && cagesOpenEvent.getRemainingSeconds() <= 5) {
                    //Send Countdown Title
                    title.setTitle(ChatColor.RED + "" + cagesOpenEvent.getRemainingSeconds());
                    title.setSubtitle(ChatColor.YELLOW + "Prepare to Fight!");
                    title.broadcast();
                }
                if (cagesOpenEvent.getRemainingSeconds() <= 0) {
                    broadcast(ChatColor.YELLOW + "Cages opened! " + ChatColor.RED + "FIGHT!");
                    //Delete Cages made of Glass
                    arena.deleteCages(Material.GLASS);
                    cagesOpenEvent.onSecondZero(this);
                    nextEvent = EventType.PVP_ENABLED;
                }
                break;
            case PVP_ENABLED:
                pvpEnabledEvent.downSecond();
                if (pvpEnabledEvent.getRemainingSeconds() <= 0) {
                    pvpEnabledEvent.onSecondZero(this);
                    nextEvent = EventType.BORDER_SHRINK;
                }
                break;
            case BORDER_SHRINK:
                borderShrinkEvent.downSecond();
                if (borderShrinkEvent.getRemainingSeconds() <= 0) {
                    borderShrinkEvent.onSecondZero(this);
                    nextEvent = EventType.SECOND_STAGE;
                }
                break;
            case SECOND_STAGE:
                secondStageEvent.downSecond();
                if (secondStageEvent.getRemainingSeconds() <= 0) {
                    secondStageEvent.onSecondZero(this);
                    nextEvent = EventType.NETHER_CLOSE;//TODO
                }
                break;
        }
    }

    private AbstractEvent getEvent(EventType type) {
        return AbstractEvent.getEvent(type);
    }

    public EventType getNextEvent() {
        return nextEvent;
    }

    public int getNextEventSeconds() {
        return getEvent(nextEvent).getRemainingSeconds();
    }

    public Arena getArena() {
        return arena;
    }

    public BorderManager getBorderManager() {
        return borderManager;
    }

    @Override
    public void onStateEnter() {
        //Register the Game Listener
        SpeedUHC.get().getGameRegistry().register(new GameListener());
        //Teleport to cages
        arena.teleportAll();
        //Create all the players' scoreboards
        Bukkit.getOnlinePlayers().forEach(this::createScoreboardForPlayer);
    }

    @Override
    public void onStateExit() {
    }

    private void createScoreboardForPlayer(Player p) {
        Scoreboard scoreboard = SimpleScoreboard.createScoreboard(p);
        scoreboard.setHandler(new ScoreboardHandler() {
            @Override
            public String getTitle(Player player) {
                return ChatColor.YELLOW + "" + ChatColor.BOLD + GameManager.getGameManager().getGameName();
            }

            @Override
            public List<Entry> getEntries(Player player) {
                return new EntryBuilder()
                        .blank()
                        .next("Next Event: ")
                        .next(ChatColor.GREEN + getEvent(nextEvent).getDisplay() + " " + NumberUtil.secondsToString(getNextEventSeconds()))
                        .blank()
                        .next("Surface Players: " + ChatColor.GREEN + "TODO")//TODO spectators
                        .next("Nether Players: " + ChatColor.GREEN + "TODO")
                        .blank()
                        .next("Kills: " + ChatColor.GREEN + GamePlayer.getPlayer(player).getKills())
                        .next("Assists: " + ChatColor.GREEN + GamePlayer.getPlayer(player).getAssists())
                        .blank()
                        .next("World Border: ")
                        .next(ChatColor.YELLOW + "+" + (int) borderManager.getWorldBorder().getSize())
                        .blank()
                        .next(ChatColor.YELLOW + "Author: M-AJ")
                        .build();
            }
        });
        scoreboard.setUpdateInterval(20L);
        scoreboard.activate();
    }
}
