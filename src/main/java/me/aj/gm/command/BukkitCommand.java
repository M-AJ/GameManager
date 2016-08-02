package me.aj.gm.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitCommand extends org.bukkit.command.Command {

	private final CommandExecutor executor;

	public BukkitCommand(String name, List<String> aliases, CommandExecutor executor) {
		super(name);
		this.executor = executor;
		setAliases(aliases);
		setLabel(name);
	}

	@Override
	public boolean execute(CommandSender sender, String tag, String[] args) {
		return executor.onCommand(sender, this, tag, args);
	}
}
