package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FrogMorph extends Morph {

    public FrogMorph() {
        this.morphName("frog")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.FROG)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_FROG_AMBIENT)
                .headId("d0edcb898356fd400c205c6134e098610bafbbc50417729a155ce77d7bca98d4");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".jump-boost")) {
            PotionEffect jump = PotionEffectType.JUMP.createEffect(999999, 5);
            this.potionEffect(jump);
        }
    }
}
