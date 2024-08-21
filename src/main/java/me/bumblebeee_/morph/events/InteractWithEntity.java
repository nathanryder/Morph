package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class InteractWithEntity implements Listener {

    MorphManager mm = new MorphManager();
    Messages m = new Messages();
    String prefix = m.getMessage("prefix");

    private HashMap<Player, Integer> giantcd;
    private HashMap<Player, Integer> beecd;
    private HashMap<Player, Integer> goatcd;
    private HashMap<Player, BukkitRunnable> cdTask;

    public InteractWithEntity() {
        giantcd = new HashMap<>();
        beecd = new HashMap<>();
        goatcd = new HashMap<>();
        cdTask = new HashMap<>();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        Entity t = e.getRightClicked();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;
        String using = mm.getUsing(p);

        if (MorphManager.toggled.contains(p.getUniqueId()))
            return;

        if (using.equalsIgnoreCase("giant")) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean("giant.throw"))
                return;

            if (!(giantcd.containsKey(p))) {
                int force = Config.MOB_CONFIG.getConfig().getInt("giant.force");
                t.setVelocity(new Vector(0, force, 0));

                if (Config.MOB_CONFIG.getConfig().getInt("giant.ability-cooldown") > 0) {
                    giantcd.put(p, Config.MOB_CONFIG.getConfig().getInt("giant.ability-cooldown"));
                    cdTask.put(p, new BukkitRunnable() {

                        public void run() {
                            giantcd.put(p, giantcd.get(p) - 1);
                            if (giantcd.get(p) <= 0) {
                                giantcd.remove(p);
                                cdTask.remove(p);
                                cancel();
                            }
                        }

                    });
                    cdTask.get(p).runTaskTimer(Morph.pl, 20, 20);
                }
            } else {
                p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, giantcd.get(p)));
            }
        } else if (using.equalsIgnoreCase("bee")) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean("bee.sting"))
                return;

            if (!(beecd.containsKey(p))) {
                if (!(t instanceof LivingEntity))
                    return;

                LivingEntity le = (LivingEntity) t;
                PotionEffect poison = PotionEffectType.POISON.createEffect(15*20, 2);
                le.addPotionEffect(poison);

                if (Config.MOB_CONFIG.getConfig().getInt("bee.ability-cooldown") > 0) {
                    beecd.put(p, Config.MOB_CONFIG.getConfig().getInt("bee.ability-cooldown"));
                    cdTask.put(p, new BukkitRunnable() {

                        public void run() {
                            beecd.put(p, beecd.get(p) - 1);
                            if (beecd.get(p) <= 0) {
                                beecd.remove(p);
                                cdTask.remove(p);
                                cancel();
                            }
                        }

                    });
                    cdTask.get(p).runTaskTimer(Morph.pl, 20, 20);
                }
            } else {
                p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, beecd.get(p)));
            }
        } else if (using.equalsIgnoreCase("goat")) {
            if (!Config.MOB_CONFIG.getConfig().getBoolean("goat.throw"))
                return;
            if (!p.isSneaking())
                return;

            if (!(goatcd.containsKey(p))) {
                int force = Config.MOB_CONFIG.getConfig().getInt("goat.force");
                t.setVelocity(t.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(force));

                int cd = Config.MOB_CONFIG.getConfig().getInt("goat.ability-cooldown");
                if (cd != 0) {
                    goatcd.put(p, cd);
                    cdTask.put(p, new BukkitRunnable() {
                        public void run() {
                            goatcd.put(p, goatcd.get(p) - 1);
                            if (goatcd.get(p) <= 1) {
                                goatcd.remove(p);
                                cdTask.remove(p);
                                cancel();
                            }
                        }
                    });
                    cdTask.get(p).runTaskTimer(Morph.pl, 20, 20);
                }
            } else {
                p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, goatcd.get(p)));
            }
        }
    }
}
