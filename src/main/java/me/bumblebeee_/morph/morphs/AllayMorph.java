package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class AllayMorph extends Morph implements Flyable {

    final String MORPH_NAME = "allay";

    public AllayMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.4);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ALLAY)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM)
                .hasBabyType(false)
                .headId("beea845cc0b58ff763decffe11cd1c845c5d09c3b04fe80b0663da5c7c699eb3");
    }
}
