package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class ZoglinMorph extends Morph {

    public ZoglinMorph() {
        this.morphName("zoglin")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ZOGLIN)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_ZOGLIN_AMBIENT)
                .hasBabyType(false)
                .headId("c19b7b5e9ffd4e22b890ab778b4795b662faff2b4978bf815574e48b0e52b301")
                .abilityInfo("&cNo abilities found");
    }
}
