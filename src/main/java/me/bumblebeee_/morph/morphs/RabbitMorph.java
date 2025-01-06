package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.utils.VersionedPotionEffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;

public class RabbitMorph extends Morph {

    final String MORPH_NAME = "rabbit";

    public RabbitMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.5);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.RABBIT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_RABBIT_AMBIENT)
                .headId("cd1d2bbc3d77ab0d119c031ea74d8781c93285cf736abc422baec1eb1560e8ca")
                .abilityInfo("&5Passive: &eJump boost 6");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".jump-boost")) {
            PotionEffect jump = new PotionEffect(VersionedPotionEffectType.JUMP_BOOST.get(), 999999, 5, false ,false);
            this.potionEffect(jump);
        }
    }
}
