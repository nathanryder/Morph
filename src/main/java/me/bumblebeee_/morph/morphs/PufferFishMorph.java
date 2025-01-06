package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PufferFishMorph  extends Morph {

    Messages msgs = new Messages();

    public PufferFishMorph() {
        this.morphName("pufferfish")
                .internalName("craftpufferfish")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PUFFERFISH)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_PUFFER_FISH_AMBIENT)
                .hasBabyType(false)
                .headId("292350c9f0993ed54db2c7113936325683ffc20104a9b622aa457d37e708d931")
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
            PotionEffect grace = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 0, false ,false);
            this.potionEffect(grace);
        }

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".waterbreathing")) {
            PotionEffect waterbreathing = new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 7, false ,false);
            this.potionEffect(waterbreathing);
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

            PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 150, 0, false ,false);
            for (Entity near : p.getNearbyEntities(8, 8, 8)) {
                if (!(near instanceof LivingEntity))
                    continue;

                ((LivingEntity) near).addPotionEffect(poison);
            }

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

}
