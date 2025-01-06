package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GoatMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public GoatMorph() {
        this.morphName("goat")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.GOAT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_GOAT_AMBIENT)
                .headId("a662336d8ae092407e58f7cc80d20f20e7650357a454ce16e3307619a0110648");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity t = e.getRightClicked();

        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".throw"))
            return;
        if (!p.isSneaking())
            return;
        if (!(t instanceof LivingEntity))
            return;

        int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
        if (existingCd >= 0) {
            p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
            return;
        }

        int force = Config.MOB_CONFIG.getConfig().getInt("goat.force");
        t.setVelocity(t.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(force));

        int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
        Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
    }

}
