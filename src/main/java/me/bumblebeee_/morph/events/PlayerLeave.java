package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerLeave implements Listener {
	
	Plugin pl = null;
	public PlayerLeave(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		final Player p = e.getPlayer();

		if (DisguiseAPI.isDisguised(p)) {
			final String type = DisguiseAPI.getDisguise(p).getType().toString().toLowerCase();
			if (!Main.using.containsKey(p.getUniqueId()))
				return;

			if (Main.health) {
				p.setHealthScale(20.0);
				p.setMaxHealth(20.0);
				p.setHealth(20.0);
			}
			if (!p.hasPermission("morph.fly")) {
				p.setAllowFlight(false);
				p.setFlying(false);
			}
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());

			//store last used morph?
			if (Main.pl.getConfig().getBoolean("persistMorphs")) {
				File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				fileConfig.set("lastMorph", Main.using.get(p.getUniqueId()));
				try {
					fileConfig.save(userFile);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}

			Main.using.remove(p.getUniqueId());
			DisguiseAPI.undisguiseToAll(p);

			MorphManager morphManager = Main.getMorphManager();
			if (morphManager.soundDisabled.contains(p.getUniqueId()))
				morphManager.soundDisabled.remove(p.getUniqueId());

			int morphCooldown = Main.pl.getConfig().getInt(type + ".morph-cooldown");
			if (morphCooldown > 0) {
				if (morphManager.typeCooldown.containsKey(p.getUniqueId())) {
					Map<String, Integer> cooldown = morphManager.typeCooldown.get(p.getUniqueId());
					morphManager.typeCooldown.remove(p.getUniqueId());

					cooldown.put(type, morphCooldown);
					morphManager.typeCooldown.put(p.getUniqueId(), cooldown);
				} else {
					Map<String, Integer> cooldown = new HashMap<>();
					cooldown.put(type, morphCooldown);
					morphManager.typeCooldown.put(p.getUniqueId(), cooldown);
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						Map<String, Integer> cooldown = morphManager.typeCooldown.get(p.getUniqueId());
						int time = cooldown.get(type) - 1;
						cooldown.remove(type);

						if (time != 0)
							cooldown.put(type, time);
						else {
							cancel();
							return;
						}

						morphManager.typeCooldown.put(p.getUniqueId(), cooldown);
					}
				}.runTaskTimer(Main.pl, 20, 20);
			}
		}
	}

}
