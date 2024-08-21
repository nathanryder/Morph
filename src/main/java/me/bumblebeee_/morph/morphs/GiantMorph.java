package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.*;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GiantMorph extends Morph implements Listener {

    Messages msgs = new Messages();

    private HashMap<Player, Double> blockcd = new HashMap<>();
    private HashMap<Player, BukkitRunnable> cdTask = new HashMap<>();

    public GiantMorph() {
        this.morphName("giant")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.GIANT)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_ZOMBIE_AMBIENT)
                .headId("MHF_Zombie")
                .abilityInfo("&5Passive: &eSlowness 3 and throws blocks wherever you walk","&5Ability: &eThrows players away from you");

        if (Config.MOB_CONFIG.isSettingTrue(getMorphName() + ".slow")) {
            PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 99999, 2, false ,false);
            this.potionEffect(slow);
        }

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ())
            return;

        final Player p = e.getPlayer();
        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (!isMorphedAsThis(p))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean(getMorphName() + ".walk-throw"))
            return;

        if (!(blockcd.containsKey(p))) {
            Location typeLoc = p.getLocation().clone();
            Material m = typeLoc.subtract(0,1,0).getBlock().getType();
            for (int i = 0; i < 3; i++) {
                if (m == Material.AIR) {
                    m = typeLoc.subtract(0, 1, 0).getBlock().getType();
                }
            }

            for (int i = 0; i <= 2; i++) {
                Utils.throwb(p, m, p.getLocation());
            }

            blockcd.put(p, 0.4);
            cdTask.put(p, new BukkitRunnable() {

                public void run() {
                    blockcd.put(p, blockcd.get(p) - 1);
                    if (blockcd.get(p) <= 0) {
                        blockcd.remove(p);
                        cdTask.remove(p);
                        cancel();
                    }
                }

            });
            cdTask.get(p).runTaskTimer(Main.pl, 4, 4);
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        Entity t = e.getRightClicked();

        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (!isMorphedAsThis(p))
            return;
        if (Main.getMorphManager().toggled.contains(p.getUniqueId()))
            return;
        if (!Config.MOB_CONFIG.getConfig().getBoolean("giant.throw"))
            return;

        int existingCd = Cooldown.getCooldown(p.getUniqueId(), getMorphName());
        if (existingCd >= 0) {
            p.sendMessage(msgs.getMessage("prefix") + " " + msgs.getMessage("cooldown", "", p.getDisplayName(), toFriendly(), existingCd));
            return;
        }

        int force = Config.MOB_CONFIG.getConfig().getInt("giant.force");
        t.setVelocity(new Vector(0, force, 0));

        int cd = Config.MOB_CONFIG.getConfig().getInt(getMorphName() + ".ability-cooldown");
        Cooldown.createCooldown(p.getUniqueId(), getMorphName(), cd);
    }

}
