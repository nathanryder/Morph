package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class FoxMorph extends Morph {

    final String MORPH_NAME = "fox";

    public FoxMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.5);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.FOX)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_FOX_AMBIENT)
                .headId("16db7d507389a14bbec39de6922165b32d43657bcb6aaf4b5182825b22b4")
                .abilityInfo("&cNo abilities found");
    }
}
