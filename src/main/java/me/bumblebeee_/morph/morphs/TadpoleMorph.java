package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TadpoleMorph extends Morph {

    public TadpoleMorph() {
        this.morphName("tadpole")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.TADPOLE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_TADPOLE_FLOP)
                .hasBabyType(false)
                .headId("987035f5352334c2cba6ac4c65c2b9059739d6d0e839c1dd98d75d2e77957847");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            this.potionEffect(waterbreathing);
        }
    }
}
