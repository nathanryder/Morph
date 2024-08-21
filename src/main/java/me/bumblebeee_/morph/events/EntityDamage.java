package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class EntityDamage implements Listener {

    MorphManager mm = new MorphManager();
    Plugin pl = null;
	public EntityDamage(Plugin plugin) {
		pl = plugin;
	}

	@EventHandler
	public void onEntityDamage(final EntityDamageEvent ev) {

		final Entity e = ev.getEntity();
		if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getEntity();
        if (!Main.using.containsKey(p.getUniqueId()))
            return;

        if (!PlayerUndisguise.blow.contains(p.getUniqueId()))
            PlayerUndisguise.blow.add(p.getUniqueId());
	}
}
