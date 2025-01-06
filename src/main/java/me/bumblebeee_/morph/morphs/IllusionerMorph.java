package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class IllusionerMorph extends Morph {

    final String MORPH_NAME = "illusioner";

    public IllusionerMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1.95);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ILLUSIONER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_ILLUSIONER_AMBIENT)
                .hasBabyType(false)
                .headId("512512e7d016a2343a7bff1a4cd15357ab851579f1389bd4e3a24cbeb88b")
                .abilityInfo("&cNo abilities found");
    }
}
