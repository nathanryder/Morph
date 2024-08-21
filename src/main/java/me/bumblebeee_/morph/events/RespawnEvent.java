package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import me.libraryaddict.disguise.DisguiseAPI;


public class RespawnEvent implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        if (Morph.health) {
            p.setHealthScale(20.0);
            p.setMaxHealth(20.0);
            p.setHealth(20.0);
        }
        if (!p.hasPermission("morph.fly")) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        DisguiseAPI.undisguiseToAll(p);
    }
 }