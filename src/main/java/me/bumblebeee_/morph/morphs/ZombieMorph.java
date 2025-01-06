package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.Runnables;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ZombieMorph extends Morph implements Listener {

    final String MORPH_NAME = "zombie";

    public ZombieMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ZOMBIE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_ZOMBIE_AMBIENT)
                .headId("64528b3229660f3dfab42414f59ee8fd01e80081dd3df30869536ba9b414e089")
                .abilityInfo("&5Passive: &eCan safely eat rotten flesh","&5Weakness: Burns in daylight")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!Runnables.raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        }
                    }
                }, 20);

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @Override
    public void initMorph(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isMorphedAsThis(p))
                    cancel();

                if (Runnables.raining) {
                    if (p.getHealth() - 0.5 <= 0) {
                        p.setHealth(0);
                    } else {
                        p.setHealth(p.getHealth() - 0.5);
                    }
                }

            }
        }.runTaskTimer(Main.pl, 0, 20);

    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.ROTTEN_FLESH)
            return;

        Player p = e.getPlayer();
        if (!isMorphedAsThis(p))
            return;

        if (Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".eat")) {
            ItemStack remove = new ItemStack(e.getItem().getType(), 1);
            p.getInventory().removeItem(remove);
            p.setFoodLevel(p.getFoodLevel() + 6);
            e.setCancelled(true);
        }
    }
}
