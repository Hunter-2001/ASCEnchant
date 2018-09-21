package com.ascendpvp.events;

import com.ascendpvp.ASCEnchantMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class HarvestHoeListen implements Listener {
	
    ASCEnchantMain plugin;
    public HarvestHoeListen(ASCEnchantMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void caneBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!e.getPlayer().getItemInHand().hasItemMeta()) return;
        if (!e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) return;
        if (!e.getPlayer().getItemInHand().getItemMeta().hasLore()) return;
        if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.harvesthoe_name")))) return;
        if (!e.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', "&7Harvest I"))) return;
        if (e.getBlock().getType() != Material.SUGAR_CANE_BLOCK) return;
        e.setCancelled(true);
        int scane = 1;
        for (int x = 2; x > 0; --x) {
            if (e.getBlock().getLocation().add(0.0, x, 0.0).getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                e.getBlock().getLocation().add(0.0, x, 0.0).getBlock().setType(Material.AIR);
                ++scane;
            }
        }
        e.getPlayer().getInventory().addItem(new ItemStack[]{new ItemStack(Material.SUGAR_CANE, scane)});
        e.getBlock().setType(Material.AIR);
    }
}

