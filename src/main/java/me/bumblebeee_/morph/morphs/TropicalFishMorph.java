package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TropicalFishMorph  extends Morph {

    public TropicalFishMorph() {
        this.morphName("tropical_fish")
                .internalName("craftfish")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.TROPICAL_FISH)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_TROPICAL_FISH_AMBIENT)
                .headId("cfbdc0722549ce9f72fed18ce4f278f27ca32a03965c635778b54d05d3ca80f4")
                .abilityInfo("&5Passive: &eDolphins grace 2")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (!Main.using.containsKey(p.getUniqueId()))
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

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".grace")) {
            PotionEffect grace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 0);
            this.potionEffect(grace);
        }

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
            this.potionEffect(waterbreathing);
        }

    }
}
