package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class EvokerMorph extends Morph implements Listener {

    Messages msgs = new Messages();
    Random r = new Random();

    public EvokerMorph() {
        this.morphName("evoker")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.EVOKER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_EVOKER_AMBIENT)
                .hasBabyType(false)
                .headId("d954135dc82213978db478778ae1213591b93d228d36dd54f1ea1da48e7cba6")
                .abilityInfo("&5Ability 1: &eSpawn vexes around you", "&5Ability 2: Create evoker fangs on the ground");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;

        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".spawnVex"))
                return;

            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName() + ":spawn");
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            int c = r.nextInt(3) + 2;
            for (int i = 0; i <= c; i++) {
                p.getWorld().spawnEntity(p.getLocation().add(0, 1, 0), EntityType.VEX);
            }

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".spawnVex-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName() + ":spawn", cd);
        } else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".attack"))
                return;

            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            Location l = p.getLocation();
            BlockFace f = Utils.yawToFace(l.getYaw());
            for (int i = 1; i < 16; i++) {
                l = Utils.forward(f, l.clone());
                EvokerFangs a = (EvokerFangs) p.getWorld().spawnEntity(l, EntityType.EVOKER_FANGS);
                a.setOwner(p);
            }

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".attack-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }

    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player))
            return;
        Player p = (Player) e.getTarget();
        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (!isMorphedAsThis(p))
            return;

        e.setCancelled(true);
    }

}
