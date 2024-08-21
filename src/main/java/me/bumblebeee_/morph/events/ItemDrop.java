package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.MorphManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDrop implements Listener {

    MorphManager morph = new MorphManager();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop() == null)
            return;

        if (!e.getItemDrop().getItemStack().isSimilar(morph.getMorphItem()))
            return;

        e.setCancelled(true);
    }

}
