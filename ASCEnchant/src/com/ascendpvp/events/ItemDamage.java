package com.ascendpvp.events;

import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamage implements Listener {
	
    ASCEnchantMain plugin;
    public ItemDamage(ASCEnchantMain plugin) {
        this.plugin = plugin;
    }
	ItemManager im = new ItemManager();

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        ItemStack item = e.getItem();
        if (im.hasEnchant(item, "Fortify I") && ((int) (Math.random() * plugin.getConfig().getDouble("fortify_one_value") + 1.0)) == 4) {
            e.setCancelled(true);
        }
        if (im.hasEnchant(item, "Fortify II") && ((int)(Math.random() * plugin.getConfig().getDouble("fortify_two_value") + 1.0)) == 1) {
            e.setCancelled(true);
        }
    }
}

