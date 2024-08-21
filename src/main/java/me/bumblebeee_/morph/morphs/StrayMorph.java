package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class StrayMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public StrayMorph() {
        this.morphName("stray")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.STRAY)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_STRAY_AMBIENT)
                .hasBabyType(false)
                .headId("6572747a639d2240feeae5c81c6874e6ee7547b599e74546490dc75fa2089186")
                .abilityInfo("&5Ability: &eShoots a slowness arrow","&5Weakness: Burns during the day")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!Runnables.raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        }
                    }
                }, 20);

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".shoot"))
            return;
        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            Arrow a = p.launchProjectile(Arrow.class);
            a.setMetadata("morph", new FixedMetadataValue(Main.pl, "yes:stray"));

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

    @EventHandler
    public void onProjHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".slow"))
            return;

        Arrow a = (Arrow) e.getEntity();
        List<MetadataValue> v = a.getMetadata("morph");
        if (!(v.size() > 0))
            return;

        if (!v.get(0).value().equals("yes:stray"))
            return;

        if (e.getHitEntity() != null) {
            if (e.getHitEntity() instanceof LivingEntity) {
                LivingEntity t = (LivingEntity) e.getHitEntity();
                for (PotionEffect p : t.getActivePotionEffects()) {
                    if (p.getType() == PotionEffectType.SLOW)
                        t.removePotionEffect(PotionEffectType.SLOW);
                }
                PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 600, 0, false ,false);
                t.addPotionEffect(slow);
            }
        }
        a.remove();
    }

}
