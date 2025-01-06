package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class SilverfishMorph extends Morph {

    final String MORPH_NAME = "silverfish";

    public SilverfishMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.2);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SILVERFISH)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_SILVERFISH_AMBIENT)
                .hasBabyType(false)
                .headId("da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540")
                .abilityInfo("&cNo abilities found");
    }
}
