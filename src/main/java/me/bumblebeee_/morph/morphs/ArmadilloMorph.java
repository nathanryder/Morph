package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class ArmadilloMorph extends Morph {

    final String MORPH_NAME = "armadillo";

    public ArmadilloMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.5);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ARMADILLO)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_ARMADILLO_AMBIENT)
                .hasBabyType(false)
                .headId("9164ed0e0ef69b0ce7815e4300b4413a4828fcb0092918543545a418a48e0c3c");
    }
}
