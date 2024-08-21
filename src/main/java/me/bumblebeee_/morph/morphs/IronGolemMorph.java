package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.events.PlayerUndisguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IronGolemMorph extends Morph implements Listener {

    public IronGolemMorph() {
        this.morphName("iron_golem")
                .internalName("craftirongolem")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.IRON_GOLEM)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_IRON_GOLEM_ATTACK)
                .headId("e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697")
                .abilityInfo("&5Passive: &eNo fall damage and Strength 6","&5Weakness: Slowness 1");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".strength")) {
            PotionEffect strength = PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 5);
            PotionEffect slow = PotionEffectType.SLOW.createEffect(999999, 1);
            this.potionEffect(strength).potionEffect(slow);
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent ev) {
        Entity e = ev.getEntity();
        if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getEntity();
        if (!isMorphedAsThis(p))
            return;

        if (ev.getCause() == EntityDamageEvent.DamageCause.FALL) {
            ev.setCancelled(true);
        }
    }
}