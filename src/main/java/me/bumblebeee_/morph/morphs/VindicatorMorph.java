package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class VindicatorMorph extends Morph {

    public VindicatorMorph() {
        this.morphName("vindicator")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.VINDICATOR)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_VINDICATOR_AMBIENT)
                .hasBabyType(false)
                .headId("6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173")
                .abilityInfo("&cNo abilities found");
    }
}
