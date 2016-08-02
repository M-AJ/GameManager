package me.aj.gm;

import com.google.common.collect.Lists;
import me.aj.gm.command.BukkitCommand;
import me.aj.gm.command.Command;
import me.aj.gm.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

public class GameRegistry {

    private CommandMap commandMap;

    private List<CommandInfo> commands;

    GameRegistry() {
        this.commands = Lists.newArrayList();
        try {
            Field field = SimplePluginManager.class
                    .getDeclaredField("commandMap");
            field.setAccessible(true);
            this.commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (Exception ex) {
            GameManager.LOGGER.log(Level.SEVERE, "Could not register command map");
            ex.printStackTrace();
        }

    }

    public void register(Listener listener) {
        GameManager.getGameManager().getServer().getPluginManager().registerEvents(listener, GameManager.getGameManager());
    }

    public void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void register(Command command) {
        String[] aliases = command.getInfo().aliases();
        String cmdName = aliases[0];
        List<String> listAliases = Lists.newArrayList();
        for (String s : aliases) {
            listAliases.add(s);
        }
        listAliases.remove(cmdName);
        org.bukkit.command.Command cmd = new BukkitCommand(cmdName,
                listAliases, command);
        if (commandMap.getCommand(cmdName) == null) {
            commandMap.register(cmdName, cmd);
        }
        commands.add(command.getInfo());
    }

    public List<CommandInfo> getCommands() {
        return commands;
    }
}
