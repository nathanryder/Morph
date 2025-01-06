package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Sound;

public class BreezeMorph extends Morph {

    public BreezeMorph() {
        this.morphName("breeze")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BREEZE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_BREEZE_IDLE_AIR)
                .headId("a275728af7e6a29c88125b675a39d88ae9919bb61fdc200337fed6ab0c49d65c")
                .abilityInfo("TODO: This is a beta build");

        this.setConfigOption(getMorphName() + ".enabled", true);
        this.setConfigOption(getMorphName() + ".health", 30);
        this.setConfigOption(getMorphName() + ".requiredKills", 1);
        this.setConfigOption(getMorphName() + ".morph-time", 0);
        this.setConfigOption(getMorphName() + ".morph-cooldown", 0);
        this.setConfigOption(getMorphName() + ".ability-cooldown", 10);
        this.buildConfig();
//        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

}
