package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player))
            return;
        Player p = (Player) e.getTarget();

        if (Main.using.containsKey(p.getUniqueId())) {
            if (Main.pl.getConfig().getBoolean("ignoreMobsWhenMorphed"))
                e.setCancelled(true);
        }
    }

}
