package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DolphinMorph extends Morph {

    public DolphinMorph() {
        this.morphName("dolphin")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.DOLPHIN)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_DOLPHIN_AMBIENT)
                .hasBabyType(false)
                .headId("8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3")
                .abilityInfo("&5Passive: &eDolphins grace 2");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".grace")) {
            PotionEffect dolphinGrace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 999999, 1, false ,false);
            this.potionEffect(dolphinGrace);
        }
    }
}
