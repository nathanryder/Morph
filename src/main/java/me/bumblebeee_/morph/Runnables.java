package me.bumblebeee_.morph;

import me.bumblebeee_.morph.events.EntityDamageByEntityListener;
import me.bumblebeee_.morph.morphs.Flyable;
import me.bumblebeee_.morph.morphs.Morph;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Runnables {

    static ManaManager mana = new ManaManager();
    static Messages m = new Messages();


	public static boolean raining = false;
    public static List<UUID> sounds = new ArrayList<>();

    public static void potionEffects() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (!Main.using.containsKey(p.getUniqueId()))
                        continue;

                    String using = Main.getMorphManager().getUsing(p);
                    Morph morph = Main.getMorphManager().getMorphType(using);

                    for (PotionEffect effect : morph.getEffects()) {
                        p.addPotionEffect(effect);
                    }
                }
            }
        }.runTaskTimer(Main.pl, 0, 20);

    }

	public static void mobSounds() {
        if (ManaManager.version != null) {
            if (ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                return;
        }
        new BukkitRunnable() {
			@Override
			public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (!Main.using.containsKey(p.getUniqueId()))
                        continue;
                    if (!p.isSneaking())
                        continue;
                    if (Main.getMorphManager().soundDisabled.contains(p.getUniqueId()))
                        continue;

                    if (sounds.contains(p.getUniqueId())) {
                        Sound s = Main.getMorphManager().playSound(p);
                        if (s != null)
                            p.getWorld().playSound(p.getLocation(), s, 2, 1);
                        sounds.remove(p.getUniqueId());
                    } else {
                        sounds.add(p.getUniqueId());
                    }
                }
			}
		}.runTaskTimer(Main.pl, 0, 10);
	}

    public static void morphPower() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (!mana.getManaPlayers().containsKey(p.getUniqueId()))
                        continue;
                    if (!hasFlyingAbility(p))
                        continue;
                    if (!Main.pl.getConfig().getBoolean("morph-power"))
                        continue;
                    if (Main.getMorphManager().morphTimeout.get(p.getUniqueId()) != null) {
                        if (Main.getMorphManager().morphTimeout.get(p.getUniqueId()) <= 30)
                            continue;
                    }

                    if (p.isFlying()) {
                        if (mana.getMana(p) < 0) {
                            p.setFlying(false);
                            p.setAllowFlight(false);
                            p.sendMessage(m.getMessage("outOfMorphPower"));
                        } else {
                            StringBuilder msg = new StringBuilder(m.getMessage("morphPower"));
                            //One | == 2 mana

                            for (int i = 1; i <= 50; i++) {
                                if (mana.getManaPlayers().get(p.getUniqueId()) < i * 2) {
                                    msg.append("§c|");
                                } else {
                                    msg.append("|");
                                }
                            }
                            ManaManager.ab.sendActionbar(p, msg.toString());
                            double take = Main.pl.getConfig().getDouble("morphPower-use");
                            mana.takeMana(p, take);
                        }
                    } else {
                        if (mana.getMana(p) == 100) {
                            ManaManager.ab.sendActionbar(p, "");
                            continue;
                        }

                        if (mana.getMana(p) > 0) {
                            p.setAllowFlight(true);
                        } else {
                            p.setAllowFlight(false);
                        }
                        double give = Main.pl.getConfig().getDouble("morphPower-regain");
                        mana.addMana(p, give);

                        StringBuilder msg = new StringBuilder(m.getMessage("morphPower"));
                        for (int i = 1; i <= 50; i++) {
                            if (mana.getManaPlayers().get(p.getUniqueId()) < i * 2) {
                                msg.append("§c|");
                            } else {
                                msg.append("|");
                            }
                        }
                        ManaManager.ab.sendActionbar(p, msg.toString());
                    }
                }
            }
        }.runTaskTimer(Main.pl, 20, 20);
    }

    public static void agroMobs() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<UUID, Map<UUID, Integer>> newAttacking = new HashMap<>();
                for (UUID uuid : EntityDamageByEntityListener.attacking.keySet()) {
                    Map<UUID, Integer> entities = EntityDamageByEntityListener.attacking.get(uuid);
                    Map<UUID, Integer> newEntities = new HashMap<>();
                    for (UUID en : entities.keySet()) {
                        if (entities.get(en) > 1) {
                            newEntities.put(en, entities.get(en) - 1);
                        }
                    }

                    newAttacking.put(uuid, newEntities);
                }

                EntityDamageByEntityListener.attacking = newAttacking;
            }
        }.runTaskTimer(Main.pl, 0, 20);
    }

    public static boolean hasFlyingAbility(Player p) {
        String using = Main.getMorphManager().getUsing(p);
        Morph morph = Main.getMorphManager().getMorphType(using);

        return morph instanceof Flyable;
    }

}