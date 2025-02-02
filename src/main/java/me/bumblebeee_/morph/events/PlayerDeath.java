package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Messages;
import me.bumblebeee_.morph.utils.Utils;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class PlayerDeath implements Listener {

	Messages m = new Messages();
	String prefix = m.getMessage("prefix");
	
	Plugin pl = null;
	public PlayerDeath(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player k = p.getKiller();
		World w = p.getWorld();

		if (k != null) {
			if (Main.using.containsKey(k.getUniqueId()) && Main.pl.getConfig().getBoolean("overrideDeathMessage")) {
				String victim = p.getName();
				String killer = k.getName();
				String killerMorph = Main.using.get(k.getUniqueId());

				List<String> messages = Main.pl.getConfig().getStringList("deathMessages");
				String msg = "An error has occurred with Morph selecting a death message";

				if (Main.pl.getConfig().getBoolean("randomMessage")) {
					Random rand = new Random();
					msg = messages.get(rand.nextInt(messages.size()));
				} else {
					msg = messages.get(0);
				}

				if (msg != null) {
					msg = msg.replace("{victim}", victim).replace("{killer}", killer);
					msg = msg.replace("{killerMob}", killerMorph).replace("{world}", w.getName());

				}
				e.setDeathMessage(msg);
			}
		}

		if (!Main.using.containsKey(p.getUniqueId())) {
			return;
		}

		if (Utils.getVersion(false) >= 21.0) {
			AttributeInstance scale = p.getAttribute(Attribute.GENERIC_SCALE);
			if (scale != null) {
				scale.setBaseValue(1);
			}
		}

		File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
		String using = Main.getMorphManager().getUsing(p);
		List<String> stringList = fileConfig.getStringList("Mobs");

		if (k != null) {
			File userFilek = new File(pl.getDataFolder() + "/UserData/" + k.getUniqueId() + ".yml");
			FileConfiguration fileConfigk = YamlConfiguration.loadConfiguration(userFilek);
			List<String> stringListk = fileConfigk.getStringList("Mobs");
			if (pl.getConfig().getBoolean("steal-morphs")) {
				if (!using.isEmpty()) {
					Main.using.remove(k.getUniqueId());
					stringList.remove(using);
					stringListk.add(using);

					fileConfig.set("Mobs", stringList);
					fileConfigk.set("Mobs", stringListk);

					try {
						fileConfig.save(userFile);
						fileConfigk.save(userFilek);
					} catch (IOException ex1) {
						ex1.printStackTrace();
					}

					if (!stringListk.contains(using)) {
						k.sendMessage(prefix + " " + m.getMessage("getMorphByKill", p.getDisplayName(), k.getDisplayName(), using, ""));
					}
				}
			}
		}

        if (!DisguiseAPI.isDisguised(p)) {
            return;
        }
        String mob = DisguiseAPI.getDisguise(p).getType().toString().toLowerCase();

		if (pl.getConfig().getBoolean("death-reset-all")) {
			if (!p.hasPermission("morph.bypassreset.all")) {
				fileConfig.set("Mobs", null);
				fileConfig.set("progress", null);
				try {
                    fileConfig.save(userFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    p.sendMessage(prefix + " " + "An error has occured. Please report this to a Admin");
                }

                p.sendMessage(prefix + " " + m.getMessage("diedLostAll", e.getEntity().getDisplayName(), "", "", ""));
			}
		} else if (pl.getConfig().getBoolean("death-reset-current")) {
			if (!p.hasPermission("morph.bypassreset." + mob)) {
				stringList.remove(mob);
				fileConfig.set("Mobs", stringList);
				fileConfig.set("progress." + mob, 0);
                try {
                    fileConfig.save(userFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (k == null) {
                    p.sendMessage(prefix + " " + m.getMessage("diedLostCurrent", "", p.getDisplayName(), using, ""));
                } else {
                    p.sendMessage(prefix + " " + m.getMessage("diedLostCurrent", k.getDisplayName(), p.getDisplayName(), using, ""));
                }

            }
		}

		if (pl.getConfig().getBoolean("stayMorphedOnDeath")) {
			if (pl.getConfig().getBoolean("death-reset-current")) {
				Main.getMorphManager().unmorphPlayer(p, false, false, false);
			} else if (pl.getConfig().getBoolean("death-reset-all")) {
				Main.getMorphManager().unmorphPlayer(p, false, false, false);
			} else {
				Main.respawnBuffer.put(p.getUniqueId(), Main.using.get(p.getUniqueId()));
			}
		} else {
			if (Main.health) {
				p.resetMaxHealth();
			}
			if (!p.hasPermission("morph.fly")) {
				p.setAllowFlight(false);
				p.setFlying(false);
			}
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());

			Main.using.remove(p.getUniqueId());
		}

	}
}
