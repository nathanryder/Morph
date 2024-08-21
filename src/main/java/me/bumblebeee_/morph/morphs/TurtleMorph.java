package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TurtleMorph extends Morph {

    public TurtleMorph() {
        this.morphName("turtle")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.TURTLE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_TURTLE_AMBIENT_LAND)
                .abilityInfo("&5Passive: &eWater breathing 8, slowness 1 and damage resistance 3")
                .headId("MHF_Turtle");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            this.potionEffect(waterbreathing);
        }
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".damageresistance")) {
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            this.potionEffect(waterbreathing);
        }
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".nightvision")) {
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            this.potionEffect(waterbreathing);
        }
    }
}
