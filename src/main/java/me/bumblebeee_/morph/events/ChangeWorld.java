package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class ChangeWorld implements Listener {
	
	Plugin pl = null;
	public ChangeWorld(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		if (!pl.getConfig().getStringList("enabled-worlds").contains(e.getPlayer().getWorld().getName())) {
			if (!pl.getConfig().getList("enabled-worlds").contains("<all>")) {
				Player p = e.getPlayer();
				
				if (DisguiseAPI.isDisguised(p)) {
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
					p.sendMessage("You have been unmorphed because morphing is disabled in this world!");
				}
			}
		}
	}

}
