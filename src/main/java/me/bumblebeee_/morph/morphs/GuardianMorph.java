package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GuardianMorph extends Morph {

    public GuardianMorph() {
        this.morphName("guardian")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.GUARDIAN)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_GUARDIAN_AMBIENT)
                .headId("18d2a7fea7f2e0d916c7c6d7914937bb8dd3fbfd7f9483a4a3912f5a0fc63d3")
                .abilityInfo("&5Passive: &eWater breathing 8 and night vision 3");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            PotionEffect nightVision = PotionEffectType.NIGHT_VISION.createEffect(999999, 2);
            this.potionEffect(waterbreathing).potionEffect(nightVision);
        }

    }
}
