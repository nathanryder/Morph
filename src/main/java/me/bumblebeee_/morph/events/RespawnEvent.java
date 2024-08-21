package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.MorphManager;
import me.bumblebeee_.morph.morphs.Morph;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.AgeableWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.ZombieWatcher;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import me.libraryaddict.disguise.DisguiseAPI;


public class RespawnEvent implements Listener {

    MorphManager mm = new MorphManager();

    Plugin pl = null;
    public RespawnEvent(Plugin plugin) {
        pl = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        if (pl.getConfig().getBoolean("stayMorphedOnDeath")) {
            boolean isBaby = false;
            String mobDis = Main.respawnBuffer.get(p.getUniqueId());
            if (mobDis.contains("baby")) {
                isBaby = true;
            }

            mm.morphPlayer(p, Main.getMorphManager().getMorphType(mobDis.split(":")[0]), true, isBaby);
            Main.respawnBuffer.remove(p.getUniqueId());
        } else {
            if (Main.health) {
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
 }