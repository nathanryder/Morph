package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
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

public class SalmonMorph  extends Morph {

    final String MORPH_NAME = "salmon";

    public SalmonMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.3);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SALMON)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_SALMON_AMBIENT)
                .hasBabyType(false)
                .headId("8aeb21a25e46806ce8537fbd6668281cf176ceafe95af90e94a5fd84924878")
                .abilityInfo("&5Passive: &eDolphins grace 2")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (!Main.using.containsKey(p.getUniqueId()))
                                continue;
                            Block in = p.getLocation().getBlock();

                            if (in.getType() == Material.AIR) {

                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        }
                    }
                }, 20);

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".grace")) {
            PotionEffect grace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 0, false ,false);
            this.potionEffect(grace);
        }

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            this.potionEffect(waterbreathing);
        }

    }
}
