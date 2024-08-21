package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
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
                .abilityInfo("UPDATE THIS");

//        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

}
