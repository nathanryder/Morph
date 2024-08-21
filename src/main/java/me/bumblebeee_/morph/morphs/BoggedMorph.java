package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class BoggedMorph extends Morph {

    public BoggedMorph() {
        this.morphName("bogged")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BOGGED)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_BOGGED_AMBIENT)
                .headId("a3b9003ba2d05562c75119b8a62185c67130e9282f7acbac4bc2824c21eb95d9")
                .abilityInfo("UPDATE THIS");

//        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

}
