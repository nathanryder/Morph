package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagmaCubeMorph extends Morph {

    public MagmaCubeMorph() {
        this.morphName("magma_cube")
                .internalName("craftmagmacube")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.MAGMA_CUBE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_MAGMA_CUBE_SQUISH)
                .headId("MHF_LavaSlime")
                .abilityInfo("&cNo abilities found");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".fireresistance")) {
            PotionEffect fireres = PotionEffectType.FIRE_RESISTANCE.createEffect(999999, 7);
            this.potionEffect(fireres);
        }

    }
}
