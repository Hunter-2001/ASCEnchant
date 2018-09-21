package com.ascendpvp.commands;

import com.ascendpvp.ASCEnchantMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EnchantConfigReload implements CommandExecutor {

	ASCEnchantMain plugin;
	public EnchantConfigReload(ASCEnchantMain plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		//Checks
		if (!cmd.getName().equals("ascenchants")) return false;
		if (!sender.hasPermission("12345")) return false;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', plugin.getConfig().getString("messages.incorrect_syntax_reload")));
			return false;
		}
		
		//Reload Config
		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', plugin.getConfig().getString("messages.reload_success")));
		}
		return false;
	}
}

