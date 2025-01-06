package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HorseMorph extends Morph {

    final String MORPH_NAME = "horse";

    public HorseMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1.6);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.HORSE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_HORSE_AMBIENT)
                .headId("be78c4762674dde8b1a5a1e873b33f28e13e7c102b193f683549b38dc70e0")
                .abilityInfo("&5Passive: &eSpeed 4");

        if (Config.MOB_CONFIG.isSettingTrue("horse.speed")) {
            PotionEffect horseSpeed = new PotionEffect(PotionEffectType.SPEED, 999999, 3, false ,false);
            this.potionEffect(horseSpeed);
        }
    }

}
