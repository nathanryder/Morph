package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.utils.VersionedPotionEffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GlowSquidMorph extends Morph {

    final String MORPH_NAME = "glow_squid";

    public GlowSquidMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.8);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craftglowsquid")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.GLOW_SQUID)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_GLOW_SQUID_AMBIENT)
                .hasBabyType(false)
                .headId("3e94a1bb1cb00aaa153a74daf4b0eea20b8974522fe9901eb55aef478ebeff0d")
                .runnable(new BukkitRunnable() {
                    PotionEffect squidBlind = new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1, false ,false);
                    PotionEffect squidSlow = new PotionEffect(VersionedPotionEffectType.SLOWNESS.get(), 99999, 3, false ,false);

                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            Block b = p.getLocation().getBlock();
                            if (b.getType() == Material.WATER) {
                                p.removePotionEffect(VersionedPotionEffectType.SLOWNESS.get());
                                p.removePotionEffect(PotionEffectType.BLINDNESS);
                            } else {
                                p.addPotionEffect(squidSlow, true);
                                p.addPotionEffect(squidBlind, true);
                                p.removePotionEffect(PotionEffectType.WATER_BREATHING);
                                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            }
                        }

                    }
                }, 20);

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2, false ,false);

            this.potionEffect(waterbreathing);
            this.potionEffect(nightVision);
        }

    }

    @Override
    public void initMorph(Player p) {



    }
}
