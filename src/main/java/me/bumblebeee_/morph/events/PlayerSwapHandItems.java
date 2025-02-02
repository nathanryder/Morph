package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.managers.Inventorys;
import me.bumblebeee_.morph.managers.Messages;
import me.bumblebeee_.morph.Main;
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
        if (!Main.pl.getConfig().getBoolean("swapMenu"))
            return;
        Player p = e.getPlayer();

        if (!p.isSneaking())
            return;
        if (!p.hasPermission("morph.morph")) {
            p.sendMessage(m.getMessage("prefix") + " " + m.getMessage("noPermissions"));
            return;
        }

        if (Main.pl.getConfig().getBoolean("onlyIfEmptyHand")) {
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