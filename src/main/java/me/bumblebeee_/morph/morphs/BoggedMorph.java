package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.managers.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionType;

public class BoggedMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    public BoggedMorph() {
        this.morphName("bogged")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.BOGGED)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_BOGGED_AMBIENT)
                .headId("a3b9003ba2d05562c75119b8a62185c67130e9282f7acbac4bc2824c21eb95d9")
                .abilityInfo("&5Ability: &eAllows you to shoot poison arrows");

        this.setConfigOption(getMorphName() + ".enabled", true);
        this.setConfigOption(getMorphName() + ".health", 16);
        this.setConfigOption(getMorphName() + ".requiredKills", 1);
        this.setConfigOption(getMorphName() + ".shoot", true);
        this.setConfigOption(getMorphName() + ".morph-time", 0);
        this.setConfigOption(getMorphName() + ".morph-cooldown", 0);
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

            Arrow a = p.launchProjectile(Arrow.class);
            a.setBasePotionType(PotionType.POISON);
            a.setMetadata("morph", new FixedMetadataValue(Main.pl, "yes"));

            int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
            Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
        }
    }

}
