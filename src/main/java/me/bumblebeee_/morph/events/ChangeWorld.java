package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class ChangeWorld implements Listener {
	
	Plugin pl = null;
	Messages m = new Messages();
	public ChangeWorld(Plugin plugin) {
		pl = plugin;
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		World w = p.getWorld();
		if (!pl.getConfig().getStringList("enabled-worlds").contains(w.getName())) {
			if (!pl.getConfig().getList("enabled-worlds").contains("<all>")) {

				if (DisguiseAPI.isDisguised(p)) {
					if (!Main.using.containsKey(p.getUniqueId()))
						return;
					if (Main.health) {
						p.resetMaxHealth();
						p.setHealth(20.0);
					}
					if (!p.hasPermission("morph.fly")) {
						p.setAllowFlight(false);
						p.setFlying(false);
					}
				    for (PotionEffect effect : p.getActivePotionEffects())
				        p.removePotionEffect(effect.getType());
				    
					Main.using.remove(p.getUniqueId());
					DisguiseAPI.undisguiseToAll(p);
					p.sendMessage( m.getMessage("prefix") + " " + m.getMessage("unmorphedByWorld"));
				}
			}
		} else {
			if (!DisguiseAPI.isDisguised(p))
				return;
			if (Main.getMorphManager().getViewMorph(p))
				return;

			Bukkit.getServer().getScheduler().runTaskLater(Main.pl, new Runnable() {
				@Override
				public void run() {
					Disguise d = DisguiseAPI.getDisguise(p);
					d.setViewSelfDisguise(false);
					DisguiseAPI.disguiseToAll(p, d);
				}
			}, 1);


		}
	}

}
