package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

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
			if (!Morph.using.containsKey(p.getUniqueId()))
				return;

			if (Morph.health) {
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

			Morph.using.remove(p.getUniqueId());
			DisguiseAPI.undisguiseToAll(p);

			if (MorphManager.soundDisabled.contains(p.getUniqueId()))
				MorphManager.soundDisabled.remove(p.getUniqueId());

			int morphCooldown = Morph.pl.getConfig().getInt(type + ".morph-cooldown");
			if (morphCooldown > 0) {
				if (MorphManager.typeCooldown.containsKey(p.getUniqueId())) {
					Map<String, Integer> cooldown = MorphManager.typeCooldown.get(p.getUniqueId());
					MorphManager.typeCooldown.remove(p.getUniqueId());

					cooldown.put(type, morphCooldown);
					MorphManager.typeCooldown.put(p.getUniqueId(), cooldown);
				} else {
					Map<String, Integer> cooldown = new HashMap<>();
					cooldown.put(type, morphCooldown);
					MorphManager.typeCooldown.put(p.getUniqueId(), cooldown);
				}

				new BukkitRunnable() {
					@Override
					public void run() {
						Map<String, Integer> cooldown = MorphManager.typeCooldown.get(p.getUniqueId());
						int time = cooldown.get(type) - 1;
						cooldown.remove(type);

						if (time != 0)
							cooldown.put(type, time);
						else {
							cancel();
							return;
						}

						MorphManager.typeCooldown.put(p.getUniqueId(), cooldown);
					}
				}.runTaskTimer(Morph.pl, 20, 20);
			}
		}
	}

}
