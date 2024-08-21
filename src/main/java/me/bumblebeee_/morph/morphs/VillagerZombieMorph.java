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

public class VillagerZombieMorph extends Morph implements Listener {

    public VillagerZombieMorph() {
        this.morphName("zombie_villager")
                .internalName("craftvillagerzombie")
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.ZOMBIE_VILLAGER)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_ZOMBIE_AMBIENT)
                .headId("75ab0527ecb313aab1413e65353aacd8672bc91e98b8a09c1a1ae78bb1db9681")
                .abilityInfo("&5Passive: &eCan safely eat rotten flesh");

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
