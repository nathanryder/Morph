package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Inventorys;
import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItems implements Listener {

    Messages m = new Messages();
    Inventorys inv = new Inventorys();

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        if (!Morph.pl.getConfig().getBoolean("swapMenu"))
            return;
        Player p = e.getPlayer();

        if (!p.isSneaking())
            return;
        if (!p.hasPermission("morph.morph")) {
            p.sendMessage(m.getMessage("prefix") + " " + m.getMessage("noPermissions"));
            return;
        }

        if (Morph.pl.getConfig().getBoolean("onlyIfEmptyHand")) {
            if (e.getOffHandItem().getType() != Material.AIR)
                return;
            inv.openMorph(p, 1);
            e.setCancelled(true);
        } else {
            inv.openMorph(p, 1);
            e.setCancelled(true);
        }
    }

}