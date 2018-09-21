package com.ascendpvp.events;

import com.ascendpvp.utils.ItemManager;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

	private static Set<Material> ignoredBlocks = new HashSet<Material>();
	static {
		ignoredBlocks.add(Material.BEDROCK);
		ignoredBlocks.add(Material.MOB_SPAWNER);
		ignoredBlocks.add(Material.STATIONARY_WATER);
		ignoredBlocks.add(Material.WATER);
		ignoredBlocks.add(Material.STATIONARY_LAVA);
		ignoredBlocks.add(Material.LAVA);
	}
	ItemManager im = new ItemManager();

	@EventHandler
	public void onObbyBreak(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack handItem = player.getItemInHand();

		if (handItem == null || handItem.getType() == Material.AIR) return;
		if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.OBSIDIAN) {

			//Check if pickaxe has enchant
			if (!im.hasEnchant(handItem, "ObbyDestroyer I")) return;
			MPlayer mPlayer = MPlayer.get(player);
			Faction factionAtBlock = BoardColl.get().getFactionAt(PS.valueOf((Location)e.getClickedBlock().getLocation()));

			//Break block naturally
			if (factionAtBlock != null && factionAtBlock.isNone() || factionAtBlock.getMPlayers().contains(mPlayer)) {
				e.getClickedBlock().breakNaturally();
			}
		}
	}

	@EventHandler
	public void onGodPick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack handItem = player.getItemInHand();

		if(handItem == null || handItem.getType() == Material.AIR) return;
		if(e.getAction() != Action.LEFT_CLICK_BLOCK) return;

		//Check if pickaxe has enchant
		if (!im.hasTwoEnchant(handItem, "ObbyDestroyer I", "Drill II")) return;
		if (player.isSneaking()) return;
		MPlayer mPlayer = MPlayer.get(player);
		Faction factionAtBlock = BoardColl.get().getFactionAt(PS.valueOf((Location)e.getClickedBlock().getLocation()));
		Location location = e.getClickedBlock().getLocation();

		//Break block clicked, and a 5x5 radius
		if (factionAtBlock != null && factionAtBlock.isNone() || factionAtBlock.getMPlayers().contains(mPlayer)) {
			e.getClickedBlock().breakNaturally();
		}
		for (int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; ++x) {
			for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; ++y) {
				for (int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; ++z) {
					Block block = new Location(location.getWorld(), x, y, z).getBlock();
					if (BlockBreak.ignoredBlocks.contains(block.getType())) continue;
					Faction factionAtTrench = BoardColl.get().getFactionAt(PS.valueOf(block));
					if ((factionAtTrench != null && factionAtTrench.isNone()) || factionAtTrench.getMPlayers().contains(mPlayer)) {
						block.breakNaturally(player.getItemInHand());
					}
				}
			}
		}
	}

	@EventHandler(ignoreCancelled=true)
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getPlayer().getItemInHand();
		
		//Null checks etc.
		if (item == null || item.getType() == Material.AIR) return;
		if (!im.hasEnchant(item, "Drill II")) return;
		if (player.isSneaking()) return;
		
		Location location = e.getBlock().getLocation();
		MPlayer mPlayer = MPlayer.get((Object)player);
		
		for (int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; ++x) {
			for (int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; ++y) {
				for (int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; ++z) {
					final Block block = new Location(location.getWorld(), (double)x, (double)y, (double)z).getBlock();
					if (!BlockBreak.ignoredBlocks.contains(block.getType())) {
						Faction factionAtTrench = BoardColl.get().getFactionAt(PS.valueOf(block));
						if ((factionAtTrench != null && factionAtTrench.isNone()) || factionAtTrench.getMPlayers().contains(mPlayer)) {
							block.breakNaturally(player.getItemInHand());
						}
					}
				}
			}
		}
	}
}

