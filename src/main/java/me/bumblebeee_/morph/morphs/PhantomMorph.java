package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PhantomMorph extends Morph implements Flyable {

    final String MORPH_NAME = "phantom";

    public PhantomMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.9);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PHANTOM)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_PHANTOM_AMBIENT)
                .hasBabyType(false)
                .headId("MHF_Phantom")
                .abilityInfo("&5Passive: &eNight vision 3", "&5Weakness: &eBurns in the daylight")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (p.getWorld().getTime() >= 0 && p.getWorld().getTime() < 13000) {
                                if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                    p.setFireTicks(60);
                                }
                            }
                        }
                    }
                }, 20);

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".nightvision")) {
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2, false ,false);
            this.potionEffect(nightVision);
        }
    }
}
