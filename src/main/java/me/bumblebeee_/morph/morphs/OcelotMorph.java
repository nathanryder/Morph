package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OcelotMorph extends Morph {

    public OcelotMorph() {
        this.morphName("ocelot")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.OCELOT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_OCELOT_AMBIENT)
                .headId("5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1")
                .abilityInfo("&5Passive: &eSpeed 7");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".speed")) {
            PotionEffect ocelotSpeed = new PotionEffect(PotionEffectType.SPEED, 999999, 6, false ,false);
            this.potionEffect(ocelotSpeed);
        }
    }
}
