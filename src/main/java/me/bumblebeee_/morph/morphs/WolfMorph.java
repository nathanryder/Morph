package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class WolfMorph extends Morph {

    final String MORPH_NAME = "wolf";

    public WolfMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.8);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.WOLF)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_WOLF_AMBIENT)
                .headId("a70611e0734caaa9d6b915d515f4aa5aa88032e902dab4d6a231f05d4fceade7")
                .abilityInfo("&cNo abilities found");
    }
}
