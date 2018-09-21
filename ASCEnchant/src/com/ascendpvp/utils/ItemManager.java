package com.ascendpvp.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

	public ItemStack nameItem(ItemStack item, String name, String lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> itemLore = new ArrayList<String>();
		itemLore.add(lore);
		meta.setLore(itemLore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack nameTwoItem(ItemStack item, String name, String lore, String lore2) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> itemLore = new ArrayList<String>();
		itemLore.add(lore);
		itemLore.add(lore2);
		meta.setLore(itemLore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack nameItem(Material item, String name, String lore) {
		return nameItem(new ItemStack(item), name, lore);
	}

	public ItemStack nameItem(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack nameItem(Material item, String name, String lore1, String lore2) {
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(lore1);
		lores.add(lore2);
		return nameItem(new ItemStack(item), name, lores);
	}

	public boolean hasEnchant(final ItemStack item, final String lore) {
		final List<String> itemLore = (List<String>)item.getItemMeta().getLore();
		return itemLore != null && itemLore.contains(ChatColor.GRAY + lore);
	}
	
    public boolean hasTwoEnchant(ItemStack item, String lore, String lore2) {
        List<String> itemLore = item.getItemMeta().getLore();
        if (itemLore != null && itemLore.contains(ChatColor.GRAY + lore) && itemLore.contains(ChatColor.GRAY + lore2)) {
            return true;
        }
        return false;
    }
}
