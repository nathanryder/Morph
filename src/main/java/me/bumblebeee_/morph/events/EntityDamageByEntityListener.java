package me.bumblebeee_.morph.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityDamageByEntityListener implements Listener {

    public static Map<UUID, Map<UUID, Integer>> attacking = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player))
            return;

        int AGRO_TIME = 20;

        Player p = (Player) e.getDamager();
        Entity en = e.getEntity();
        Map<UUID, Integer> entities;

        if (attacking.containsKey(p.getUniqueId())) {
            entities = attacking.get(p.getUniqueId());
        } else {
            entities = new HashMap<>();
        }

        entities.remove(en.getUniqueId());
        entities.put(en.getUniqueId(), AGRO_TIME);

        attacking.put(p.getUniqueId(), entities);
    }

}
