package me.bumblebeee_.morph;

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
        configFile = new File(Main.pl.getDataFolder(), file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Main.pl.saveResource(file, false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public boolean isEnabled(String mob) {
        return isSettingTrue(mob + ".enabled");
    }

    public int getHealth(String mob) {
        return getConfig().getInt(mob + ".health");
    }

    public int getRequiredKills(String mob) {
        String exists = Config.MOB_CONFIG.getConfig().getString(mob);
        if (exists == null) {
            return 1;
        }

        return getConfig().getInt(mob + ".requiredKills");
    }

    public int getMorphTime(String mob) {
        String exists = Config.MOB_CONFIG.getConfig().getString(mob);
        if (exists == null) {
            return 0;
        }

        return getConfig().getInt(mob + ".morph-time");
    }

    public int getMorphCooldown(String mob) {
        String exists = Config.MOB_CONFIG.getConfig().getString(mob);
        if (exists == null) {
            return 10;
        }

        return getConfig().getInt(mob + ".morph-cooldown");
    }

    public boolean isSettingTrue(String setting) {
        if (getConfig().getString(setting) == null)
            return true;

        return getConfig().getBoolean(setting);
    }

    public FileConfiguration getConfig() {
        return customConfig;
    }

    public File getFile() {
        return configFile;
    }
}
