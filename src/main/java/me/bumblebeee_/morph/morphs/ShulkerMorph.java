package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class ShulkerMorph extends Morph {

    public ShulkerMorph() {
        this.morphName("shulker")
                .internalName("craftgolem")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SHULKER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_SHULKER_AMBIENT)
                .hasBabyType(false)
                .headId("6b309bf1bb6dc678c23da30a0f6e80357625f6314eaf3659db2c4106d684f979")
                .abilityInfo("&cNo abilities found");
    }
}
