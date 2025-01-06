package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagmaCubeMorph extends Morph {

    final String MORPH_NAME = "magma_cube";

    public MagmaCubeMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craftmagmacube")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.MAGMA_CUBE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_MAGMA_CUBE_SQUISH)
                .hasBabyType(false)
                .headId("38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429")
                .abilityInfo("&cNo abilities found");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".fireresistance")) {
            PotionEffect fireres = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 7, false ,false);
            this.potionEffect(fireres);
        }

    }
}
