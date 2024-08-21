package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BatMorph extends Morph implements Flyable {

    public BatMorph() {
        this.morphName("bat")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BAT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_BAT_AMBIENT)
                .hasBabyType(false)
                .headId("6681a72da7263ca9aef066542ecca7a180c40e328c0463fcb114cb3b83057552")
                .abilityInfo("&5Passive: &eFlying and night vision 3");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".nightVision")) {
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2, false ,false);
            this.potionEffect(nightVision);
        }
    }
}
