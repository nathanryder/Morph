package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class VexMorph extends Morph implements Listener, Flyable {

    Messages msgs = new Messages();

    public VexMorph() {
        this.morphName("vex")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.VEX)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_VEX_AMBIENT)
                .headId("5e7330c7d5cd8a0a55ab9e95321535ac7ae30fe837c37ea9e53bea7ba2de86b")
                .abilityInfo("&cAbility: &ePhase through walls");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".phase"))
            return;
        if (!p.isSneaking())
            return;

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            Location l = p.getLocation();
            BlockFace f = Utils.yawToFace(p.getLocation().getYaw());
            Location inFront = Utils.forward(f, l.clone());
            if (!inFront.getBlock().getType().isSolid())
                return;
            if (inFront.getBlock().isLiquid())
                return;

            Location tp = null;
            int maxLayers = Config.MOB_CONFIG.getConfig().getInt("vex.max-layers");
            for (int i = 0; i <= maxLayers; i++) {
                if (tp != null)
                    continue;

                l = Utils.forward(f, l.clone());
                if (l.getBlock().getType().isSolid())
                    continue;
                if (l.getBlock().isLiquid())
                    continue;
                tp = l;
            }
            if (tp == null) {
                p.sendMessage(msgs.getMessage("vexTooManyLayers"));
                return;
            }
            p.teleport(tp);

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

}
