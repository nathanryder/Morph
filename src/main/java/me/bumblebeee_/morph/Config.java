package me.bumblebeee_.morph;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum Config {

    MOB_CONFIG("mobConfig.yml");

    private String file;
    private File configFile;
    private FileConfiguration customConfig;

    Config(String name) {
        this.file = name;
    }

    public void createOrLoad() {
        configFile = new File(Morph.pl.getDataFolder(), file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Morph.pl.saveResource(file, false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return customConfig;
    }

}
