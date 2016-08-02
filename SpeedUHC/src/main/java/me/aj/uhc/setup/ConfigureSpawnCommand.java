package me.aj.uhc.setup;

import me.aj.gm.command.Command;
import me.aj.gm.command.CommandInfo;
import me.aj.gm.util.LocationUtils;
import me.aj.uhc.SpeedUHC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(
        allowsConsole = false,
        description = "Set the spawn location of lobby and add player spawns!",
        permission = "uhc.configurespawn",
        aliases = {"configureuhc", "uhcconfig", "configuhc"}
)
public class ConfigureSpawnCommand extends Command {

    @Override
    public void execute(CommandSender cs, String tag, String[] args) {
        Player p = (Player) cs;
        if (args.length == 0) {
            SpeedUHC.get().getConfig().set("lobbySpawn", LocationUtils.locationToString(p.getLocation()));
            SpeedUHC.get().saveConfig();
            p.sendMessage(ChatColor.GREEN + "Set the spawn of the lobby!");
        } else if (args[0].equalsIgnoreCase("addSpawn")) {
            List<String> spawns = SpeedUHC.get().getConfig().getStringList("playerSpawns");
            spawns.add(LocationUtils.locationToString(p.getLocation()));
            SpeedUHC.get().getConfig().set("playerSpawns", spawns);
            SpeedUHC.get().saveConfig();
            p.sendMessage(ChatColor.GREEN + "Added a player spawn!");
        } else if (args[0].equalsIgnoreCase("center")) {
            SpeedUHC.get().getConfig().set("center", LocationUtils.locationToString(p.getLocation()));
            SpeedUHC.get().saveConfig();
            p.sendMessage(ChatColor.GREEN + "Set the center point!");
        } else {
            msg(false, p, "No such command!");
        }
    }
}
