package me.aj.gm.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {

    public abstract void execute(CommandSender cs, String tag, String[] args);

    public CommandInfo getInfo() {
        return getClass().getAnnotation(CommandInfo.class);
    }

    public void msg(boolean success, CommandSender cs, String msg) {
        ChatColor color = (success) ? ChatColor.GREEN : ChatColor.RED;
        cs.sendMessage(color + msg);
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String tag, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission(getInfo().permission())) {
                msg(false, player, "You don't have permission for that command!");
                return true;
            }
            execute(player, tag, args);
        } else if (getInfo().allowsConsole()) {
            try {
                execute(sender, tag, args);
            } catch (Exception ex) {
                ex.printStackTrace();
                msg(false, sender, "Command execution fired an exception!");
                return true;
            }
        } else {
            msg(false, sender, "This command must be executed by a in-game player!");
        }
        return true;
    }
}