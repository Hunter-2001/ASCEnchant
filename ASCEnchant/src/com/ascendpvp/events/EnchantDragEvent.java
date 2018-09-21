package com.ascendpvp.events;

import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantDragEvent implements Listener {

	ASCEnchantMain plugin;
	public EnchantDragEvent(ASCEnchantMain plugin) {
		this.plugin = plugin;
	}
	ItemManager im = new ItemManager();

	@EventHandler
	public void enchantApply(InventoryClickEvent e) {
		if (e.getCurrentItem() == null || e.getCursor() == null) return;
		if (e.getCursor().getType() != Material.MAGMA_CREAM) return;
		if (e.getCursor().getItemMeta() == null) return;
		if (!e.getCursor().getItemMeta().hasDisplayName()) return;
		ItemStack itemStack = e.getCurrentItem();
		ItemStack item = e.getCursor();
		Player p = (Player) e.getWhoClicked();
		String itemName = item.getItemMeta().getDisplayName();
		String toUpper = itemStack.getType().name().toUpperCase();
		if (!(item.getAmount() <= 1)) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.stacked_enchant_item")));
			return;
		}

		if (toUpper.contains("HELMET") || toUpper.contains("CHESTPLATE") || toUpper.contains("LEGGINGS") || toUpper.contains("BOOTS") || toUpper.contains("PICKAXE") || toUpper.contains("SWORD")) {
			if ((toUpper.contains("CHESTPLATE") || toUpper.contains("BOOTS") || toUpper.contains("LEGGINGS")) && (itemName.contains("Gills") || itemName.contains("Boost"))) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant_apply")));
				return;
			}
			if (toUpper.contains("HELMET") && itemName.contains("Boost")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant_apply")));
				return;
			}
			if ((toUpper.contains("SWORD") || toUpper.contains("AXE")) && itemName.contains("Rage")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant_apply")));
				return;
			}
			ArrayList<String> lore = new ArrayList<String>();
			if (itemStack.getItemMeta().getLore() != null) {
				lore.addAll(itemStack.getItemMeta().getLore());
			}
			if (im.hasEnchant(itemStack, item.getItemMeta().getDisplayName())) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.already_has_enchant")));
				return;
			}
			lore.add(ChatColor.GRAY + ChatColor.stripColor(item.getItemMeta().getDisplayName()));
			p.getInventory().setItem(e.getSlot(), im.nameItem(itemStack, itemStack.getItemMeta().getDisplayName(), lore));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.apply_success")));
			e.setCursor(null);
			e.setCancelled(true);
			return;
		}
	}
}

