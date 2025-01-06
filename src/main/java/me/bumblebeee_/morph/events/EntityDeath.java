package me.bumblebeee_.morph.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Messages;
import me.bumblebeee_.morph.morphs.Morph;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

public class EntityDeath implements Listener {

	Plugin pl = null;
	Messages msgs = new Messages();

	public EntityDeath(Plugin plugin) {
		pl = plugin;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent ev) {
		String prefix = msgs.getMessage("prefix");
		Player killer = ev.getEntity().getKiller();

		if (killer == null)
			return;
		if (!pl.getConfig().getList("enabled-worlds").contains(killer.getWorld().getName()) && !pl.getConfig().getList("enabled-worlds").contains("<all>"))
			return;

		File userFile = new File(pl.getDataFolder() + "/UserData/" + killer.getUniqueId() + ".yml");
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
		List<String> stringList = fileConfig.getStringList("Mobs");
		String killed = ev.getEntity().toString().toLowerCase();

		Morph morphType = null;
		boolean isBaby = false;

		for (String name : Main.getMorphManager().getMorphs().keySet()) {
			Morph morph = Main.getMorphManager().getMorphType(name);

			if (killed.startsWith(morph.getInternalName())) {
				morphType = morph;

				if (ev.getEntity() instanceof Ageable) {
					if (!((Ageable) ev.getEntity()).isAdult()) {
						isBaby = true;
					}
				}
				break;
			}
		}

		if (killed.contains("craftplayer")) {
			if (pl.getConfig().getBoolean("enable-players")) {
				if (killer.hasPermission("morph.into.player")) {

					String t = ev.getEntity().getName();
					List<String> players = fileConfig.getStringList("Players");
					if (!players.toString().contains(t)) {
						if (pl.getConfig().getStringList("blacklisted-players").contains(t)) {
							return;
						}

						players.add(t);
						fileConfig.set("Players", players);
						try {
							fileConfig.save(userFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						String msg = msgs.getMessage("youCanNowMorph", t, "", "");
						killer.sendMessage(prefix + " " + msg);
					}
				}
			}
		}

		if (morphType == null)
			return;
		String type = morphType.getMorphName();
		String fullType = type + (isBaby ? ":baby" : "");

		if (killer.hasPermission("morph.bypasskill." + type))
			return;
		if (!killer.hasPermission("morph.into." + type))
			return;
		if (stringList.contains(fullType))
			return;

		int killsRequired = morphType.getRequiredKills();
		int currKills = 0;
		if (fileConfig.get("progress." + type) != null) {
			currKills = fileConfig.getInt("progress." + type);
		}
		currKills++;

		if (currKills < killsRequired) {
			String msg = msgs.getMessage("morphProgress", currKills, killsRequired, type);
			if (msg != null) {
				killer.sendMessage(prefix + " " + msg);
			}
			fileConfig.set("progress." + type, currKills);

			try {
				fileConfig.save(userFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		stringList.add(fullType);
		fileConfig.set("Mobs", stringList);
		try {
			fileConfig.save(userFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String msgType = isBaby ? "baby " + morphType.toFriendly() : morphType.toFriendly();
		//Just incase the key doesn't exist, I forget it in earlier versions
		String msg = msgs.getMessage("youCanNowMorph", msgType, "", "");
		if (msg != null) {
			killer.sendMessage(prefix + " " + msg);
		}

		if (pl.getConfig().getBoolean("morphOnKill")) {
			Main.getMorphManager().morphPlayer(killer, morphType, false, isBaby);
		}
	}
}