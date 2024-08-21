package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class EntityDamage implements Listener {

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
