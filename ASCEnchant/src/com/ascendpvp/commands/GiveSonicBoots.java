package com.ascendpvp.commands;

import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveSonicBoots implements CommandExecutor {

	ASCEnchantMain plugin;
	public GiveSonicBoots(ASCEnchantMain plugin) {
		this.plugin = plugin;
	}
	ItemManager im = new ItemManager();

	/*
	TODO: Replace seperate classes with a single command switch. IE: /giveitem (itemname) (player)
	Instead of /(itemname) (player)
	 */

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//CMD Syntax Checks
		if (!cmd.getName().equalsIgnoreCase("sonicboots")) return false;
		if (!sender.hasPermission("12345")) return false;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.incorrect_syntax_give")));
			return false;
		}
		Player selectedPlayer = Bukkit.getPlayer((String)args[0].toLowerCase());
		String printPlayer = args[0];

		//Check if player is availible
		if (selectedPlayer == null) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.player_not_online_give").replaceAll("#selectedPlayer#", printPlayer)));
			return false;
		}
		ItemStack item = im.nameItem(Material.DIAMOND_BOOTS, ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.sonicboots_name")), ChatColor.translateAlternateColorCodes((char)'&', (String)"&7Rush III"));
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);

		//Give/Drop Item
		if (selectedPlayer.getInventory().firstEmpty() != -1) {
			selectedPlayer.getInventory().addItem(new ItemStack[]{item});
			selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.give_item_success").replaceAll("#selectedPlayer#", printPlayer).replaceAll("#givenItem#", plugin.getConfig().getString("messages.sonicboots_name"))));
		} else {
			selectedPlayer.getWorld().dropItem(selectedPlayer.getLocation(), item);
			selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.no_inv_space_give").replaceAll("#givenItem#", plugin.getConfig().getString("messages.sonicboots_name"))));
			selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)plugin.getConfig().getString("messages.give_item_success").replaceAll("#selectedPlayer#", printPlayer).replaceAll("#givenItem#", plugin.getConfig().getString("messages.sonicboots_name"))));
		}
		return false;
	}
}

