package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.utils.VersionedPotionEffectType;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;

public class FrogMorph extends Morph {

    final String MORPH_NAME = "frog";

    public FrogMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.4);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.FROG)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_FROG_AMBIENT)
                .hasBabyType(false)
                .headId("d0edcb898356fd400c205c6134e098610bafbbc50417729a155ce77d7bca98d4");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".jump-boost")) {
            PotionEffect jump = new PotionEffect(VersionedPotionEffectType.JUMP_BOOST.get(), 999999, 5, false ,false);
            this.potionEffect(jump);
        }
    }
}
