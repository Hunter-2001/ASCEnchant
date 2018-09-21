package com.ascendpvp;

import com.ascendpvp.checks.EnchantCheck;
import com.ascendpvp.commands.EnchantConfigReload;
import com.ascendpvp.commands.EnchantGui;
import com.ascendpvp.commands.GiveGodPick;
import com.ascendpvp.commands.GiveHarvestHoe;
import com.ascendpvp.commands.GiveObbyPick;
import com.ascendpvp.commands.GiveSonicBoots;
import com.ascendpvp.commands.GiveTrenchPick;
import com.ascendpvp.events.BlockBreak;
import com.ascendpvp.events.EnchantDragEvent;
import com.ascendpvp.events.HarvestHoeListen;
import com.ascendpvp.events.ItemDamage;
import com.ascendpvp.events.MobKill;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ASCEnchantMain extends JavaPlugin {
    public ArrayList<String> enchantNames1 = new ArrayList<String>();
    public ArrayList<String> enchantNames2 = new ArrayList<String>();

    public void onEnable() {
        new EnchantCheck(this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new MobKill(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemDamage(this), this);
        Bukkit.getPluginManager().registerEvents(new EnchantDragEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new HarvestHoeListen(this), this);
        getCommand("enchants").setExecutor(new EnchantGui(this));
        Bukkit.getPluginManager().registerEvents(new EnchantGui(this), this);
        getCommand("godpick").setExecutor(new GiveGodPick(this));
        getCommand("trenchpick").setExecutor(new GiveTrenchPick(this));
        getCommand("obbypick").setExecutor(new GiveObbyPick(this));
        getCommand("harvesthoe").setExecutor(new GiveHarvestHoe(this));
        getCommand("sonicboots").setExecutor(new GiveSonicBoots(this));
        getCommand("ascenchants").setExecutor(new EnchantConfigReload(this));
        saveDefaultConfig();
        setupEnchants();
    }

    private void setupEnchants() {
        enchantNames2.add("Rush");
        enchantNames1.add("Rush");
        enchantNames2.add("Springs");
        enchantNames1.add("Springs");
        enchantNames2.add("Rage");
        enchantNames2.add("Fortify");
        enchantNames1.add("Fortify");
        enchantNames1.add("Sated");
        enchantNames1.add("Gills");
        enchantNames1.add("Inferno");
        enchantNames1.add("Fortify");
        enchantNames2.add("Fortify");
        enchantNames1.add("Haste");
        enchantNames2.add("Haste");
        enchantNames1.add("Boost");
        enchantNames2.add("Boost");
    }
}

