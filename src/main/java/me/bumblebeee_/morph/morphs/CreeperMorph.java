package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Cooldown;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CreeperMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public CreeperMorph() {
        this.morphName("creeper")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.CREEPER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_CREEPER_PRIMED)
                .headId("MHF_Creeper")
                .abilityInfo("&5Passive: &eBlows up when you die","&5Ability: &eMakes you explode, dying in the process");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".explosion"))
            return;
        if (!p.isSneaking())
            return;


        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
            if (existingCd >= 0) {
                p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                return;
            }

            p.setHealth(0);

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        World w = p.getWorld();

        if (!isMorphedAsThis(p))
            return;
        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".explosion"))
            return;

        if (!Main.pl.getConfig().getBoolean("creeperDeathMessage"))
            e.setDeathMessage(null);

        w.createExplosion(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 2.0F, false, Config.MOB_CONFIG.getConfig().getBoolean("creeper.explosion-damage"));
        p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("creeperExploded", "", p.getDisplayName(), "", ""));
    }
}