package com.ascendpvp.checks;

import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import com.ascendpvp.ASCEnchantMain;
import com.ascendpvp.utils.ItemManager;

public class EnchantCheck {
    ItemManager im = new ItemManager();
    
	public EnchantCheck(ASCEnchantMain plugin) {
		new BukkitRunnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ItemStack[] armorContents;
					
					//Makes sure to not infinitely iterate
					for (int length = (armorContents = p.getInventory().getArmorContents()).length, i = 0; i < length; ++i) {
						ItemStack item = armorContents[i];
						if (item.getType() != Material.AIR) {
							if (im.hasEnchant(item, "Rush I")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 59, 0), true);
							}
							if (im.hasEnchant(item, "Rush II")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 59, 1), true);
							}
							if (im.hasEnchant(item, "Rush III")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 59, 2), true);
							}
							if (im.hasEnchant(item, "Rage II")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 59, 0), true);
							}
							if (im.hasEnchant(item, "Springs I")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 59, 1), true);
							}
							if (im.hasEnchant(item, "Springs II")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 59, 2), true);
							}
							if (im.hasEnchant(item, "Haste I")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 59, 0), true);
							}
							if (im.hasEnchant(item, "Haste II")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 59, 1), true);
							}
							if (im.hasEnchant(item, "Inferno I")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 59, 0), true);
							}
							if (im.hasEnchant(item, "Sated I")) {
								p.setFoodLevel(20);
								p.setSaturation(20);
							}
							if (im.hasEnchant(item, "Gills I")) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 59, 0), true);
							}
						}
					}
				}
			}
		}.runTaskTimer((Plugin)plugin, 0L, 55L);
	}
}
