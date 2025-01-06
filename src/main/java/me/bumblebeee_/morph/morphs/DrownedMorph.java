package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.Runnables;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DrownedMorph extends Morph {

    final String MORPH_NAME = "drowned";

    public DrownedMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.DROWNED)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_DROWNED_AMBIENT)
                .headId("303d552bff98137a4d20ef1ae6087e717a87ade2ce5a4d9044c776f0e5948a2")
                .abilityInfo("&5Passive: &eDolphins grace 2 and water breathing 8")
                .runnable(new BukkitRunnable() {
                @Override
                public void run() {
                    if (!Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".climb")) {
                        cancel();
                        return;
                    }

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

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            this.potionEffect(waterbreathing);
        }
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".grace")) {
            PotionEffect grace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 2, false ,false);
            this.potionEffect(grace);
        }

    }
}
