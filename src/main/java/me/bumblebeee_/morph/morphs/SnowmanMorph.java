package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SnowmanMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public SnowmanMorph() {
        this.morphName("snowman")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.SNOWMAN)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_SNOWBALL_THROW)
                .headId("53ad79207c3f8bbf4b1d532cfe86dccb57d2c97aeeb51ef00a660afdceab5fa9")
                .abilityInfo("&5Passive: &ePlaces snow down wherever you go, and you can shoot snowballs","&5Weakness: Starts to melt when it is raining")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (Runnables.raining) {
                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        }

                    }
                }, 20);;

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
            return;

        final Player p = e.getPlayer();
        if (!isMorphedAsThis(p))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".snow"))
            return;

        if (e.getTo().clone().add(0, -0.5, 0).getBlock().getTemperature() > 1) {
            p.setFireTicks(40);
        } else if (e.getTo().clone().add(0, -0.5, 0).getBlock().getType() == Material.WATER) {
            if (p.getHealth()-0.5 <= 0) {
                p.setHealth(0);
            } else {
                p.setHealth(p.getHealth() - 0.5);
            }
        } else if (Config.MOB_CONFIG.getConfig().getBoolean("snowman.snow")) {
            Block b = p.getLocation().getBlock();
            if (b.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
                b.setType(Material.SNOW);
            }
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".shoot"))
            return;
        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            e.setCancelled(true);
            p.launchProjectile(Snowball.class);

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }
}
