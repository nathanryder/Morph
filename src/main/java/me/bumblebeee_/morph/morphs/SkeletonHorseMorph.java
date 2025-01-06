package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkeletonHorseMorph extends Morph {

    public SkeletonHorseMorph() {
        this.morphName("skeleton_horse")
                .internalName("crafthorse{variant=skeleton_horse}")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SKELETON_HORSE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_SKELETON_HORSE_AMBIENT)
                .headId("47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a");

        if (Config.MOB_CONFIG.isSettingTrue("skeleton_horse.speed")) {
            PotionEffect horseSpeed = new PotionEffect(PotionEffectType.SPEED, 999999, 3, false ,false);
            this.potionEffect(horseSpeed);
        }
    }

}
