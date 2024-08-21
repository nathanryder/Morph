package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Cooldown;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.Messages;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BlazeMorph extends Morph implements Flyable, Listener {

    Messages msgs = new Messages();

    public BlazeMorph() {
        this.morphName("blaze")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BLAZE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_BLAZE_AMBIENT)
                .hasBabyType(false)
                .headId("MHF_Blaze")
                .abilityInfo("&5Passive: &eFlying and Fire resistance 8", "&5Ability: &eShoots a fireball")
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if (!isMorphedAsThis(p))
                                continue;

                            Block b = p.getLocation().getBlock();
                            if (b.getType() == Material.WATER) {
                                double health = p.getHealth();
                                if (health - 1 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(health - 1);
                                }
                            }
                        }

                    }
                }, 20);

        if (Config.MOB_CONFIG.isSettingTrue("blaze.fire")) {
            PotionEffect fireres = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 7, false ,false);
            this.potionEffect(fireres);
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @Override
    public void initMorph(Player p) {
        FlagWatcher watcher = DisguiseAPI.getDisguise(p).getWatcher();
        watcher.setBurning(true);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".fire"))
            return;
        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            Fireball f = p.launchProjectile(Fireball.class);
            f.setFireTicks(0);

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

}
