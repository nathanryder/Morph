package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class InteractWithEntity implements Listener {

    MorphManager mm = new MorphManager();
    Messages m = new Messages();
    String prefix = m.getMessage("prefix");

    private HashMap<Player, Integer> giantcd;
    private HashMap<Player, BukkitRunnable> cdTask;

    public InteractWithEntity() {
        giantcd = new HashMap<>();
        cdTask = new HashMap<>();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        Entity t = e.getRightClicked();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;
        String using = mm.getUsing(p);
        if (!Morph.pl.getConfig().getBoolean("giant-throw"))
            return;

        if (MorphManager.toggled.contains(p.getUniqueId()))
            return;

        if (using.equalsIgnoreCase("giant")) {
            if (!(giantcd.containsKey(p))) {
                int force = Morph.pl.getConfig().getInt("giant-force");
                t.setVelocity(new Vector(0, force, 0));

                if (Morph.pl.getConfig().getInt("cooldowns.giant") > 0) {
                    giantcd.put(p, Morph.pl.getConfig().getInt("cooldowns.giant"));
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
        }
    }
}
