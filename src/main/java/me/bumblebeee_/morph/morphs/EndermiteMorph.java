package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class EndermiteMorph extends Morph {

    public EndermiteMorph() {
        this.morphName("endermite")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ENDERMITE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_ENDERMITE_AMBIENT)
                .hasBabyType(false)
                .headId("5a1a0831aa03afb4212adcbb24e5dfaa7f476a1173fce259ef75a85855")
                .abilityInfo("&cNo abilities found");
    }
}
