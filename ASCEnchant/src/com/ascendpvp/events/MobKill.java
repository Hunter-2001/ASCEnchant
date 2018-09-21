package com.ascendpvp.events;

import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKill implements Listener {
	
    ASCEnchantMain plugin;
    public MobKill(ASCEnchantMain plugin) {
        this.plugin = plugin;
    }
	ItemManager im = new ItemManager();

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) return;
        if (!(e.getEntity().getKiller() instanceof Player)) return;
        
        int droppedExp = e.getDroppedExp();
        Player killer = e.getEntity().getKiller();
        if (killer.getItemInHand() != null && killer.getItemInHand().getType() != Material.AIR && im.hasEnchant(killer.getItemInHand(), "Boost I")) {
            droppedExp = e.getDroppedExp() * this.plugin.getConfig().getInt("boost_one_exp_multiplication");
        }
        if (killer.getItemInHand() != null && killer.getItemInHand().getType() != Material.AIR && im.hasEnchant(killer.getItemInHand(), "Boost II")) {
            droppedExp = e.getDroppedExp() * this.plugin.getConfig().getInt("boost_two_exp_multiplication");
        }
        e.setDroppedExp(droppedExp);
    }
}

