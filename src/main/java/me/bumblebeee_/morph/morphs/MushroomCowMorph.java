package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class MushroomCowMorph extends Morph {

    final String MORPH_NAME = "mushroom_cow";

    public MushroomCowMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craftmushroomcow")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.MUSHROOM_COW)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_COW_AMBIENT)
                .headId("1543b72def1b247685ad4d027df86c9632e7dac143a9552ec89c80035c3ba4ae")
                .abilityInfo("&cNo abilities found");
    }
}
