package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.utils.VersionedPotionEffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;

public class RavagerMorph extends Morph {

    final String MORPH_NAME = "ravager";

    public RavagerMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 2.2);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.RAVAGER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_RAVAGER_AMBIENT)
                .hasBabyType(false)
                .headId("3b62501cd1b87b37f628018210ec5400cb65a4d1aab74e6a3f7f62aa85db97ee");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".strength")) {
            PotionEffect ravagerStrength = new PotionEffect(VersionedPotionEffectType.RESISTANCE.get(), 999999, 6, false ,false);
            this.potionEffect(ravagerStrength);
        }
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".slowness")) {
            PotionEffect slow = new PotionEffect(VersionedPotionEffectType.SLOWNESS.get(), 999999, 1, false ,false);
            this.potionEffect(slow);
        }

    }
}
