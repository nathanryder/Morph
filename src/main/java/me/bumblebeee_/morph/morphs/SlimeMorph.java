package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.utils.VersionedPotionEffectType;
import me.bumblebeee_.morph.events.PlayerUndisguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.UUID;

public class SlimeMorph extends Morph implements Listener {

    private static HashMap<UUID, Entity> damage = new HashMap<>();

    public SlimeMorph() {
        this.morphName("slime")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SLIME)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.BLOCK_SLIME_BLOCK_STEP)
                .hasBabyType(false)
                .headId("61affd31efc37ba84f50187394d8688344ccd06cdc926ddfcf2df116986dca9")
                .abilityInfo("&5Passive: &eJump boost 4 and splits into multiple slimes when taking damage");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".jump-boost")) {
            PotionEffect slimeJump = new PotionEffect(VersionedPotionEffectType.JUMP_BOOST.get(), 999999, 3, false ,false);
            this.potionEffect(slimeJump);
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent ev) {

        final Entity e = ev.getEntity();
        if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getEntity();
        if (!isMorphedAsThis(p))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".split"))
            return;

        if (!PlayerUndisguise.blow.contains(p.getUniqueId()))
            PlayerUndisguise.blow.add(p.getUniqueId());

        if (p.getHealth() - ev.getFinalDamage() < 1) {
            ev.setCancelled(true);
            Location l = p.getLocation();
            p.setHealth(20);
            for (int i = 0; i < 2; i++) {
                final Slime slime = (Slime) l.getWorld().spawnEntity(l, EntityType.SLIME);
                slime.setSize(2);
                damage.put(p.getUniqueId(), slime);
                Bukkit.getServer().getScheduler().runTaskLater(Main.pl, new Runnable() {
                    @Override
                    public void run() {
                        damage.remove(p.getUniqueId());
                    }
                }, 100);
            }
        }

    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player))
            return;
        Player p = (Player) e.getTarget();
        if (!isMorphedAsThis(p))
            return;

        if (damage.containsKey(p.getUniqueId())) {
            if (damage.containsValue(e.getEntity())) {
                e.setCancelled(true);
            }
        }

    }
}
