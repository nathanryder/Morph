package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.managers.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.BreezeWindCharge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class BreezeMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    final String MORPH_NAME = "breeze";

    public BreezeMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BREEZE)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_BREEZE_IDLE_AIR)
                .headId("a275728af7e6a29c88125b675a39d88ae9919bb61fdc200337fed6ab0c49d65c")
                .abilityInfo("&5Ability: &eJump into the air and gain resistance for a short time");

        this.setConfigOption(getMorphName() + ".enabled", true);
        this.setConfigOption(getMorphName() + ".health", 30);
        this.setConfigOption(getMorphName() + ".requiredKills", 1);
        this.setConfigOption(getMorphName() + ".shoot", true);
        this.setConfigOption(getMorphName() + ".morph-time", 0);
        this.setConfigOption(getMorphName() + ".morph-cooldown", 0);
        this.setConfigOption(getMorphName() + ".ability-cooldown", 10);
        this.buildConfig();

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
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

            PotionEffect resistance = new PotionEffect(PotionEffectType.RESISTANCE, 20*10, 3, false ,false);
            p.addPotionEffect(resistance);
            p.setVelocity(new Vector(0, 1.5, 0));

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

}
