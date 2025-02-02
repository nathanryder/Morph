package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.Main;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PigMorph extends Morph implements Listener {

    final String MORPH_NAME = "pig";

    public PigMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.8);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.PIG)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_PIG_AMBIENT)
                .headId("7535805cd966dbe7fb3167fdf9561928c89bbc47f14b9b8fe1738fa58ad178af");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".eatGrass"))
            return;

        Player p = e.getPlayer();

        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!p.isSneaking())
                return;
            if (e.getClickedBlock() == null)
                return;

            String m = e.getClickedBlock().getType().toString();
            if (m.equals("GRASS")) {
                e.getClickedBlock().setType(Material.DIRT);
                int flevel = p.getFoodLevel();
                p.setFoodLevel(flevel + 2);
            }
        }
    }
}
