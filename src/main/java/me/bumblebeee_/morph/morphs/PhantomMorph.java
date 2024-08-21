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

public class PhantomMorph extends Morph implements Flyable {

    public PhantomMorph() {
        this.morphName("phantom")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PHANTOM)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
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
