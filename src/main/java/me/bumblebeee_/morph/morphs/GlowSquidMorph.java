package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
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

    public GlowSquidMorph() {

        this.morphName("glow_squid")
                .internalName("craftglowsquid")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.GLOW_SQUID)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_GLOW_SQUID_AMBIENT)
                .headId("3e94a1bb1cb00aaa153a74daf4b0eea20b8974522fe9901eb55aef478ebeff0d")
                .runnable(new BukkitRunnable() {
                    PotionEffect squidBlind = PotionEffectType.BLINDNESS.createEffect(999999, 1);
                    PotionEffect squidSlow = PotionEffectType.SLOW.createEffect(999999, 3);

                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            Block b = p.getLocation().getBlock();
                            if (b.getType() == Material.WATER) {
                                p.removePotionEffect(PotionEffectType.SLOW);
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
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            PotionEffect nightVision = PotionEffectType.NIGHT_VISION.createEffect(999999, 2);

            this.potionEffect(waterbreathing);
            this.potionEffect(nightVision);
        }

    }

    @Override
    public void initMorph(Player p) {



    }
}
