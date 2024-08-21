package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class PolarBearMorph extends Morph {

    public PolarBearMorph() {
        this.morphName("polar_bear")
                .internalName("craftpolarbear")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.POLAR_BEAR)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_POLAR_BEAR_AMBIENT)
                .headId("442123ac15effa1ba46462472871b88f1b09c1db467621376e2f71656d3fbc")
                .abilityInfo("&cNo abilities found");
    }
}