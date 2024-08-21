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
                .abilityInfo("TODO: This is a beta build");

        this.setConfigOption(getMorphName() + ".enabled", true);
        this.setConfigOption(getMorphName() + ".health", 16);
        this.setConfigOption(getMorphName() + ".requiredKills", 1);
        this.setConfigOption(getMorphName() + ".shoot", true);
        this.setConfigOption(getMorphName() + ".morph-time", 0);
        this.setConfigOption(getMorphName() + ".morph-cooldown", 0);
        this.buildConfig();
//        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

}
