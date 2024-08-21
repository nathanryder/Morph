package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.VersionedPotionEffectType;
import me.bumblebeee_.morph.events.PlayerUndisguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class StriderMorph extends Morph implements Listener {

    public StriderMorph() {
        this.morphName("strider")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.STRIDER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_STRIDER_AMBIENT)
                .headId("d7e4eb0fb489d6f250c607d28d672f127ebaede8e007fa6cd34e2bbc0c2fc33a")
                .abilityInfo("&5Passive: &eSpeed and fire resistance when in lava","&5Weakness: Slowness 4 when not in lava")
                .runnable(new BukkitRunnable() {
                    PotionEffect ocelotSpeed = new PotionEffect(PotionEffectType.SPEED, 999999, 6, false ,false);
                    PotionEffect fireres = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 7, false ,false);
                    PotionEffect squidSlow = new PotionEffect(VersionedPotionEffectType.SLOWNESS.get(), 999999, 3, false ,false);

                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            Block b = p.getLocation().getBlock();
                            if (b.getType() == Material.LAVA) {
                                p.removePotionEffect(VersionedPotionEffectType.SLOWNESS.get());

                                if (Config.MOB_CONFIG.getConfig().getBoolean("strider.speed")) {
                                    p.addPotionEffect(ocelotSpeed, true);
                                }
                                if (Config.MOB_CONFIG.getConfig().getBoolean("strider.fireresistance")) {
                                    p.addPotionEffect(fireres, true);
                                }
                            } else {
                                p.addPotionEffect(squidSlow, true);
                                p.removePotionEffect(PotionEffectType.SPEED);
                                p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                            }
                        }
                    }
                }, 20);

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent ev) {
        final Entity e = ev.getEntity();
        if (!(e instanceof Player))
            return;
        Player p = (Player) ev.getEntity();
        if (!isMorphedAsThis(p))
            return;

        if (ev.getCause() == EntityDamageEvent.DamageCause.LAVA || ev.getCause() == EntityDamageEvent.DamageCause.FIRE
                || ev.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            ev.setCancelled(true);
        }
    }
}
