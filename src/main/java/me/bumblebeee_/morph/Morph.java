package me.bumblebeee_.morph;

import me.bumblebeee_.morph.events.RegisterEvents;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Morph extends JavaPlugin implements Listener {

	public static Plugin pl = null;

	Messages m = new Messages();
	UpdateChecker uc = new UpdateChecker();
    ManaManager mana = new ManaManager();

	public static boolean health;
	public static HashMap<UUID, String> using = new HashMap<>();
	public static List<UUID> undisguiseBuffer = new ArrayList<>();

	public void onEnable() {
		pl = this;

        setupFiles();
        setupCommands();
        RegisterEvents.register(this);
		if (getConfig().getBoolean("morph-power")) {
			Runnables.morphPower();
		}
		mana.setup();
        Runnables.setup(this);
        Runnables.burning(this);
        Runnables.mobSounds();

        checkReload();
        Runnables.spider(this);
		health = !getConfig().getBoolean("disableHealthSystem");

//		if (getConfig().getBoolean("checkForUpdates"))
//			uc.run("8846");
		Metrics metrics = new Metrics(this);

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (MorphManager.soundDisabled.contains(p.getUniqueId()))
				continue;

			File f = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
			YamlConfiguration c = YamlConfiguration.loadConfiguration(f);

			boolean sound = c.getString("sounds") == null || c.getBoolean("sounds");
			if (!sound)
				MorphManager.soundDisabled.add(p.getUniqueId());
		}
	}

	public void onDisable() {
		if (Morph.pl.getConfig().getBoolean("persistMorphs")) {
			//store last used morph
			for (UUID uuid : Morph.using.keySet()) {
				String mob = Morph.using.get(uuid);
				File userFile = new File(pl.getDataFolder() + "/UserData/" + uuid + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				fileConfig.set("lastMorph", mob);

				try {
					fileConfig.save(userFile);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}

        using.clear();
		MorphManager.soundDisabled.clear();
        mana.getManaPlayers().clear();
	}

	public void setupCommands() {
		Bukkit.getServer().getPluginCommand("morph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("unmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("addmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("delmorph").setExecutor(new MorphCommand());
		Bukkit.getServer().getPluginCommand("forcemorph").setExecutor(new MorphCommand());
	}

	public void setupFiles() {
		saveDefaultConfig();

		for (Config config : Config.values()) {
			config.createOrLoad();
		}

		m.setup();
		File userfiles = new File(getDataFolder() + File.separator + "UserData" + File.separator);
		if (!userfiles.exists()) {
			userfiles.mkdirs();
		}
	}

	public void checkReload() {
		if (!(Bukkit.getServer().getOnlinePlayers().size() > 0))
			return;
		String prefix = m.getMessage("prefix");

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (!DisguiseAPI.isDisguised(p))
				continue;

			DisguiseAPI.undisguiseToAll(p);

			if (health) {
				p.setHealthScale(20.0);
				p.setMaxHealth(20.0);
			}
            if (!p.hasPermission("morph.fly")) {
                p.setAllowFlight(false);
                p.setFlying(false);
            }
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());

			mana.getManaPlayers().put(p.getUniqueId(), 100.0);
			p.sendMessage(prefix + " " + m.getMessage("unmorphedByStaff"));
		}
	}
}