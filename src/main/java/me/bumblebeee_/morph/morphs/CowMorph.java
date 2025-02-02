package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.managers.Cooldown;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.managers.Messages;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CowMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    final String MORPH_NAME = "cow";

    public CowMorph() {
        this.setConfigOption(MORPH_NAME + ".scale", 1);
        this.buildConfig();

        this.morphName(MORPH_NAME)
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.COW)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .scale(Config.MOB_CONFIG.getScale(getMorphName()))
                .sound(Sound.ENTITY_COW_AMBIENT)
                .headId("f88be57af6d248c297b3cae676f45cc2a438f15a9ef26cdf5f035fb842d86dd9")
                .abilityInfo("&5Ability: &eEating grass restores hunger");

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

        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!p.isSneaking())
                return;

            if (p.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)) {
                int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
                if (existingCd >= 0) {
                    p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
                    return;
                }

                ItemStack remove = new ItemStack(Material.BUCKET, 1);
                p.getInventory().removeItem(remove);
                ItemStack drop = new ItemStack(Material.MILK_BUCKET);
                drop.setAmount(1);
                p.getWorld().dropItem(p.getLocation(), drop);

                int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
                Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
            }

            if (e.getClickedBlock() == null)
                return;

            if (e.getClickedBlock().getType() == Material.GRASS_BLOCK) {
                e.getClickedBlock().setType(Material.DIRT);
                int flevel = p.getFoodLevel();
                p.setFoodLevel(flevel + 2);
            }
        }
    }
}
