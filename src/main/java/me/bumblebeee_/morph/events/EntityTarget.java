package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Map;
import java.util.UUID;

public class EntityTarget implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player))
            return;
        Player p = (Player) e.getTarget();

        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (EntityDamageByEntityListener.attacking.containsKey(p.getUniqueId())) {
            Map<UUID, Integer> attacking = EntityDamageByEntityListener.attacking.get(p.getUniqueId());

            if (!attacking.containsKey(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }

    }

}
