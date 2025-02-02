package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class ParrotMorph extends Morph implements Flyable {

    final String MORPH_NAME = "parrot";

    public ParrotMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.9);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PARROT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_PARROT_AMBIENT)
                .hasBabyType(false)
                .headId("f0bfa850f5de4b2981cce78f52fc2cc7cd7b5c62caefeddeb9cf311e83d9097")
                .abilityInfo("&5Passive: &eAllows you to fly");
    }
}
