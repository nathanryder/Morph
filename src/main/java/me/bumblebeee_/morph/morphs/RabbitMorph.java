package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.VersionedPotionEffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RabbitMorph extends Morph {

    public RabbitMorph() {
        this.morphName("rabbit")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.RABBIT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_RABBIT_AMBIENT)
                .headId("MHF_Rabbit")
                .abilityInfo("&5Passive: &eJump boost 6");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".jump-boost")) {
            PotionEffect jump = new PotionEffect(VersionedPotionEffectType.JUMP_BOOST.get(), 999999, 5, false ,false);
            this.potionEffect(jump);
        }
    }
}
