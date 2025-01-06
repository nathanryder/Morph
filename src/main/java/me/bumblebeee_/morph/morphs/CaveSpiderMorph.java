package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.managers.Messages;
import me.bumblebeee_.morph.utils.Utils;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

public class CaveSpiderMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public CaveSpiderMorph() {
        this.morphName("cave_spider")
                .internalName("craftcavespider")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.CAVE_SPIDER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_SPIDER_AMBIENT)
                .hasBabyType(false)
                .headId("41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224")
                .abilityInfo("&5Passive: &eAllows you to climb walls")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".spider-climb")) {
                            cancel();
                            return;
                        }

                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            if (p.isSneaking()) {
                                Location l = p.getLocation().add(0, 1, 0);
                                if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, 0).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, 0).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(-0.1F));
                                }
                            } else {
                                Location l = p.getLocation().add(0, 1, 0);
                                if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, 0).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, 0).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, 0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(0.5, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                } else if (!Utils.SPIDER_UNCLIMBABLE.contains(l.clone().add(-0.5, 0, -0.5).getBlock().getType())) {
                                    p.setVelocity(p.getVelocity().setY(0.4F));
                                }
                            }

                        }
                    }
                }, 20);

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".web"))
            return;
        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            final FallingBlock b = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.COBWEB));
            b.setVelocity(p.getEyeLocation().getDirection().multiply(2));

            if (Config.MOB_CONFIG.getConfig().getBoolean("cave_spider.removeSpiderWeb")) {
                int time = Config.MOB_CONFIG.getConfig().getInt("cave_spider.spiderWebRemove");
                Bukkit.getServer().getScheduler().runTaskLater(Main.pl, new Runnable() {
                    @Override
                    public void run() {
                        b.remove();
                        if (b.getLocation().getBlock().getType() == Material.COBWEB)
                            b.getLocation().getBlock().setType(Material.AIR);
                    }
                }, 20 * time);
            }

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }
}
