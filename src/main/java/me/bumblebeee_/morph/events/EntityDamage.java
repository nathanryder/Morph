package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Morph;
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

    static HashMap<UUID, Entity> damage = new HashMap<>();
	
	@EventHandler
	public void onEntityDamage(final EntityDamageEvent ev) {

		final Entity e = ev.getEntity();
		if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getEntity();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;

        if (!PlayerUndisguise.blow.contains(p.getUniqueId()))
            PlayerUndisguise.blow.add(p.getUniqueId());

        final String using = mm.getUsing(p);
        if (using.contains("chicken")) {
            if (ev.getCause() == DamageCause.FALL) {
                ev.setCancelled(true);
            }
        } else if (using.contains("slime")) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean("slime.split"))
                return;
            if (p.getHealth()-ev.getFinalDamage() < 1) {
                ev.setCancelled(true);
                Location l = p.getLocation();
                p.setHealth(20);
                for (int i = 0; i < 2; i++) {
                    final Slime slime = (Slime) l.getWorld().spawnEntity(l, EntityType.SLIME);
                    slime.setSize(2);
                    damage.put(p.getUniqueId(), slime);
                    Bukkit.getServer().getScheduler().runTaskLater(Morph.pl, new Runnable() {
                        @Override
                        public void run() {
                            if (damage.containsKey(p.getUniqueId())) {
                                damage.remove(p.getUniqueId());
                            }
                        }
                    }, 100);
                }
            }
        } else if (using.contains("husk")) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean("husk.hunger"))
                return;
        }
	}

    @EventHandler
    public void onEntityDamageEntity(final EntityDamageByEntityEvent ev) {
        Entity e = ev.getDamager();
        if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getDamager();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;

        if (!PlayerUndisguise.blow.contains(p.getUniqueId()))
            PlayerUndisguise.blow.add(p.getUniqueId());

        final String using = mm.getUsing(p);
        if (using.contains("husk")) {
            Entity t = ev.getEntity();
            if (t instanceof Animals) {
                Animals ta = (Animals) t;
                for (PotionEffect pe : ta.getActivePotionEffects()) {
                    if (pe.getType() == PotionEffectType.HUNGER)
                        ta.removePotionEffect(PotionEffectType.HUNGER);
                }
                ta.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*7, 0));
            } else if (t instanceof Monster) {
                Monster tm = (Monster) t;
                for (PotionEffect pe : tm.getActivePotionEffects()) {
                    if (pe.getType() == PotionEffectType.HUNGER)
                        tm.removePotionEffect(PotionEffectType.HUNGER);
                }
                tm.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*7, 0));
            } else if (t instanceof Player) {
                Player tp = (Player) t;
                for (PotionEffect pe : tp.getActivePotionEffects()) {
                    if (pe.getType() == PotionEffectType.HUNGER)
                        tp.removePotionEffect(PotionEffectType.HUNGER);
                }
                tp.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*7, 0));
            }
        }
    }
}
