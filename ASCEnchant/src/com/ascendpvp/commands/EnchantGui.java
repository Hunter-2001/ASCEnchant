package com.ascendpvp.commands;

import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantGui implements CommandExecutor, Listener {

	ASCEnchantMain plugin;
	public EnchantGui(ASCEnchantMain plugin) {
		this.plugin = plugin;
	}
	ItemManager im = new ItemManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//TODO: Redo naming process
		ItemStack gPane = new ItemStack(Material.STAINED_GLASS, 1, (short)plugin.getConfig().getInt("gui_glass_color"));
		ItemStack buyTier1Item = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta tier1Im = buyTier1Item.getItemMeta();
		ArrayList<String> lores1 = new ArrayList<String>();
		String printPrice1 = String.valueOf(plugin.getConfig().getInt("tier1_price"));
		lores1.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.click_to_buy_tier1").replaceAll("#tier1Price#", printPrice1)));
		tier1Im.setLore(lores1);
		tier1Im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tier1_gui_item")));
		buyTier1Item.setItemMeta(tier1Im);
		ItemStack buyTier2Item = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta tier2Im = buyTier2Item.getItemMeta();
		ArrayList<String> lores2 = new ArrayList<String>();
		String printPrice2 = String.valueOf(plugin.getConfig().getInt("tier2_price"));
		lores2.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.click_to_buy_tier2").replaceAll("#tier2Price#", printPrice2)));
		tier2Im.setLore(lores2);
		tier2Im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tier2_gui_item")));
		buyTier2Item.setItemMeta(tier2Im);
		//

		//CMD Syntax And Player checks
		if (!cmd.getName().equalsIgnoreCase("enchants")) return false;
		if (!(sender instanceof Player)) {
			sender.sendMessage("Console can't buy Custom Enchants silly!");
			return false;
		}
		Player p = (Player)sender;
		if (args.length != 0) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect_syntax")));
			return false;
		}

		PlayerInventory pInventory = p.getInventory();
		if (pInventory.firstEmpty() != -1) {
			Inventory enchantGui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.buy_enchant_gui_name")));
			
			//Setup GUI
			for (int i = 0; i < 26; i++) {
				if(i == 12) {
					enchantGui.setItem(i, buyTier1Item);
					continue;
				} else if(i == 14) {
					enchantGui.setItem(i, buyTier2Item);
					continue;
				}
				enchantGui.setItem(i, gPane);
			}
			p.openInventory(enchantGui);
		} else {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no_inv_space")));
		}
		return false;
	}

	@EventHandler
	public void onGuiClick(InventoryClickEvent e) {

		//TODO: Redo naming process
		Player ePlayer = (Player)e.getWhoClicked();
		String guiInvName = e.getInventory().getName();
		String printPrice1 = String.valueOf(plugin.getConfig().getInt("tier1_price"));
		String printPrice2 = String.valueOf(plugin.getConfig().getInt("tier2_price"));
		ItemStack buyTier1Item = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta tier1Im = buyTier1Item.getItemMeta();
		ArrayList<String> lores1 = new ArrayList<String>();
		lores1.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.click_to_buy_tier1").replaceAll("#tier1Price#", printPrice1)));
		tier1Im.setLore(lores1);
		tier1Im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tier1_gui_item")));
		buyTier1Item.setItemMeta(tier1Im);
		ItemStack buyTier2Item = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta tier2Im = buyTier2Item.getItemMeta();
		ArrayList<String> lores2 = new ArrayList<String>();
		lores2.add(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.click_to_buy_tier2").replaceAll("#tier2Price#", printPrice2)));
		tier2Im.setLore(lores2);
		tier2Im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tier2_gui_item")));
		buyTier2Item.setItemMeta(tier2Im);
		//

		//Check for special inventory
		if (e.getClickedInventory() == null) return;
		if (!guiInvName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.buy_enchant_gui_name")))) return;
		e.setCancelled(true);
		int id1 = getRandom(plugin.enchantNames1.size()) - 1;
		int id2 = getRandom(plugin.enchantNames2.size()) - 1;
		String enchant1 = plugin.enchantNames1.get(id1);
		String enchant2 = plugin.enchantNames2.get(id2);

		//Player Clicks on tier 1 (Slot 12)
		if (e.getSlot() == 12 && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			if (!(ePlayer.getLevel() >= plugin.getConfig().getInt("tier1_price"))) {
				ePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not_enough_exp")));
				return;
			}
			ePlayer.setLevel(ePlayer.getLevel() - plugin.getConfig().getInt("tier1_price"));
			ItemStack item = im.nameItem(Material.MAGMA_CREAM, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tier_one_name").replaceAll("#enchant#", enchant1)), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.name_help")));
			ePlayer.getInventory().addItem(new ItemStack[]{item});
			ePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tier1_purchase_success")));

			//Player Clicks on tier 1 (Slot 14)
		} else if (e.getSlot() == 14 && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			if (!(ePlayer.getLevel() >= plugin.getConfig().getInt("tier2_price"))) {
				ePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not_enough_exp")));
				return;
			}
			ePlayer.setLevel(ePlayer.getLevel() - plugin.getConfig().getInt("tier2_price"));
			ItemStack item = im.nameItem(Material.MAGMA_CREAM, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tier_two_name").replaceAll("#enchant#", enchant2)), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.name_help")));
			ePlayer.getInventory().addItem(new ItemStack[]{item});
			ePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tier2_purchase_success")));
		}
	}

	private int getRandom(int max) {
		return (int)(Math.random() * (double)max + 1.0);
	}
}

