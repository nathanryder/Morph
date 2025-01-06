package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class AxolotlMorph extends Morph {

    public AxolotlMorph() {
        this.morphName("axolotl")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.AXOLOTL)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_AXOLOTL_IDLE_WATER)
                .headId("e8a8a2d7ccf0c3746e23ab5491070e0923f05b235f9a2f5d53d384353853bddc")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            Block in = p.getLocation().getBlock();

                            if (in.getType() == Material.AIR) {

                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        }
                    }
                }, 20);

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            this.potionEffect(waterbreathing);
        }
    }
}
