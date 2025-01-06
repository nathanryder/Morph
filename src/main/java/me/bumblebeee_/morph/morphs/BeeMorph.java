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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BeeMorph extends Morph implements Flyable, Listener {

    Messages msgs = new Messages();

    final String MORPH_NAME = "bee";

    public BeeMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 0.4);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BEE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_BEE_LOOP)
                .headId("d7db9a6047d299a6945fa360299e12a13736d56f1fdfc192ec20f29cf46818c")
                .abilityInfo("&5Ability: &eAllows you to sting people", "&5Passive: &eAllows you to fly");

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
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".sting"))
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

        LivingEntity le = (LivingEntity) t;
        PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 15*20, 2, false ,false);
        le.addPotionEffect(poison);

        int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
        Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
    }
}
