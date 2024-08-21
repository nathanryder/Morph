package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {

    MorphManager mm = new MorphManager();

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player))
            return;
        Player p = (Player) e.getTarget();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;

        String using = mm.getUsing(p);
        Entity en = e.getEntity();
        if (using.equalsIgnoreCase("evoker")) {
            e.setCancelled(true);
        } else if (using.equalsIgnoreCase("slime")) {
            if (EntityDamage.damage.containsKey(p.getUniqueId())) {
                if (EntityDamage.damage.containsValue(e.getEntity())) {
                    e.setCancelled(true);
                }
            }
        } else if (Morph.using.containsKey(p.getUniqueId())) {
            if (Morph.pl.getConfig().getBoolean("ignoreMobsWhenMorphed"))
                e.setCancelled(true);
        } else if (en.getType() == EntityType.PHANTOM) {
            if (Morph.using.get(p.getUniqueId()).equalsIgnoreCase("cat"))
                e.setCancelled(true);
        }
    }

}
