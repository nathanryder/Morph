package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RavagerMorph extends Morph {

    public RavagerMorph() {
        this.morphName("ravager")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.RAVAGER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_RAVAGER_AMBIENT)
                .hasBabyType(false)
                .headId("3b62501cd1b87b37f628018210ec5400cb65a4d1aab74e6a3f7f62aa85db97ee");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".strength")) {
            PotionEffect ravagerStrength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 6, false ,false);
            this.potionEffect(ravagerStrength);
        }
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".slowness")) {
            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 999999, 1, false ,false);
            this.potionEffect(slow);
        }

    }
}
