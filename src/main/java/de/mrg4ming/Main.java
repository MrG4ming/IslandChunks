package de.mrg4ming;

import org.bukkit.Bukkit;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getLogger().info("Plugin successfully started. Beginning world generation...");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new IslandWorldGenerator();
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(String worldName, String id) {
        return super.getDefaultBiomeProvider(worldName, id);
    }
}
