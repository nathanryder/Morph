package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class WitherSkeletonMorph extends Morph {

    final String MORPH_NAME = "wither_skeleton";

    public WitherSkeletonMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1.2);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craftwitherskeleton")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.WITHER_SKELETON)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_WITHER_SKELETON_AMBIENT)
                .hasBabyType(false)
                .headId("2d26f2dfdf5dffc16fc80811a843524daf12c4931ec850307775c6d35a5f46c1")
                .abilityInfo("&cNo abilities found");
    }
}
