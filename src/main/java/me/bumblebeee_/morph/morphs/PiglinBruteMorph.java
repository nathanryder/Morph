package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class PiglinBruteMorph extends Morph {

    public PiglinBruteMorph() {
        this.morphName("piglin_brute")
                .internalName("craftpiglinbrute")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PIGLIN_BRUTE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_PIGLIN_BRUTE_AMBIENT)
                .headId("3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf")
                .abilityInfo("&5Ability: &eEating grass restores hunger");
    }
}
