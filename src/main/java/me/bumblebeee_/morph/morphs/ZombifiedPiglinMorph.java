package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Main;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombifiedPiglinMorph extends Morph implements Listener {

    public ZombifiedPiglinMorph() {
        this.morphName("zombified_piglin")
                .internalName("craftpigzombie")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ZOMBIFIED_PIGLIN)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_ZOMBIFIED_PIGLIN_AMBIENT)
                .headId("8954d0d1c286c1b34fb091841c06aed741a1bf9b65b9a430e4e5ca1d1c4b9f6f")
                .abilityInfo("&5Passive: &eSpeed 2 and can safely eat rotten flesh");

        if (Config.MOB_CONFIG.isSettingTrue("pig_zombie.speed")) {
            PotionEffect zombieSpeed = new PotionEffect(PotionEffectType.SPEED, 999999, 1, false ,false);
            this.potionEffect(zombieSpeed);
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.ROTTEN_FLESH)
            return;

        Player p = e.getPlayer();
        if (!isMorphedAsThis(p))
            return;

        if (Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".eat")) {
            ItemStack remove = new ItemStack(e.getItem().getType(), 1);
            p.getInventory().removeItem(remove);
            p.setFoodLevel(p.getFoodLevel() + 6);
            e.setCancelled(true);
        }
    }

}
