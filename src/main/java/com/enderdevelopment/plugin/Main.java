package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new HelpfulMobListener(), this);
        getLogger().info("Helpful mobs are active.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Helpful mobs are inactive.");
    }
}
