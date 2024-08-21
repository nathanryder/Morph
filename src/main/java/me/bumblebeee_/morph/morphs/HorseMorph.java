package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HorseMorph extends Morph {

    public HorseMorph() {
        this.morphName("horse")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.HORSE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_HORSE_AMBIENT)
                .headId("be78c4762674dde8b1a5a1e873b33f28e13e7c102b193f683549b38dc70e0")
                .abilityInfo("&5Passive: &eSpeed 4");

        if (Config.MOB_CONFIG.isSettingTrue("horse.speed")) {
            PotionEffect horseSpeed = PotionEffectType.SPEED.createEffect(999999, 3);
            this.potionEffect(horseSpeed);
        }
    }

}
