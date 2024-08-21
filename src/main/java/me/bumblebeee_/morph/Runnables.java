package me.bumblebeee_.morph;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Runnables {

    static MorphManager morph = new MorphManager();
    static ManaManager mana = new ManaManager();
    static Messages m = new Messages();


	public static boolean raining = false;
    public static List<UUID> sounds = new ArrayList<>();

	public static void spider(final Plugin pl) {
		final ArrayList<Material> blocked = new ArrayList<>(Arrays.asList(Material.AIR, Material.LAVA, Material.LEGACY_DOUBLE_PLANT));
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!Morph.using.containsKey(p.getUniqueId()))
						continue;
					String using = morph.getUsing(p);

					if (using.equalsIgnoreCase("spider") && !Config.MOB_CONFIG.getConfig().getBoolean("spider.climb"))
					    continue;
                    if (using.equalsIgnoreCase("cave_spider") && !Config.MOB_CONFIG.getConfig().getBoolean("cave_spider.spider-climb"))
                        continue;

					if (using.equalsIgnoreCase("spider") || using.equalsIgnoreCase("cave_spider")) {
						if (p.isSneaking()) {
							Location l = p.getLocation().add(0, 1, 0);
							if (!blocked.contains(l.clone().add(0.5, 0, 0).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(0, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, 0).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(0, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(0.5, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(0.5, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.1F));
							}
						} else {
							Location l = p.getLocation().add(0, 1, 0);
							if (!blocked.contains(l.clone().add(0.5, 0, 0).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(0, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, 0).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(0, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(0.5, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, 0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(0.5, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							} else if (!blocked.contains(l.clone().add(-0.5, 0, -0.5).getBlock().getType())) {
								p.setVelocity(p.getVelocity().setY(0.4F));
							}
						}
					}

				}
			}
		}.runTaskTimer(Morph.pl, 3, 3);
	}

	public static void setup(final Plugin pl) {
		new BukkitRunnable() {
		    @Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (!DisguiseAPI.isDisguised(p))
                        continue;
                    if (!Morph.using.containsKey(p.getUniqueId()))
                        continue;
                    String using = morph.getUsing(p);

                    PotionEffect slow = PotionEffectType.SLOW.createEffect(999999, 1);
                    PotionEffect strength = PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 5);
                    PotionEffect nightVision = PotionEffectType.NIGHT_VISION.createEffect(999999, 2);
                    PotionEffect turtleSlow = PotionEffectType.SLOW.createEffect(999999, 0);
                    PotionEffect squidSlow = PotionEffectType.SLOW.createEffect(999999, 3);
                    PotionEffect squidBlind = PotionEffectType.BLINDNESS.createEffect(999999, 1);
                    PotionEffect waterbreathing = PotionEffectType.WATER_BREATHING.createEffect(200, 7);
					PotionEffect jump = PotionEffectType.JUMP.createEffect(999999, 5);
					PotionEffect slimeJump = PotionEffectType.JUMP.createEffect(999999, 3);
                    PotionEffect horseSpeed = PotionEffectType.SPEED.createEffect(999999, 3);
                    PotionEffect zombieSpeed = PotionEffectType.SPEED.createEffect(999999, 1);
                    PotionEffect ocelotSpeed = PotionEffectType.SPEED.createEffect(999999, 6);
                    PotionEffect fireres = PotionEffectType.FIRE_RESISTANCE.createEffect(999999, 7);
                    PotionEffect dmgRes = PotionEffectType.DAMAGE_RESISTANCE.createEffect(999999, 2);

                    if (using.equalsIgnoreCase("squid")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("squid.waterbreathing")) {
                            p.addPotionEffect(waterbreathing);
                            p.addPotionEffect(nightVision);
                        }
                        Block b = p.getLocation().getBlock();
                        if (b.getType() == Material.WATER) {
                            p.removePotionEffect(PotionEffectType.SLOW);
                            p.removePotionEffect(PotionEffectType.BLINDNESS);
                        } else {
                            p.addPotionEffect(squidSlow, true);
                            p.addPotionEffect(squidBlind, true);
                            p.removePotionEffect(PotionEffectType.WATER_BREATHING);
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    } else if (using.equalsIgnoreCase("blaze")) {
                        p.addPotionEffect(fireres, true);
                        Block b = p.getLocation().getBlock();
                        if (b.getType() == Material.WATER) {
                            double health = p.getHealth();
                            if (health-1 <= 0) {
                                p.setHealth(0);
                            } else {
                                p.setHealth(health - 1);
                            }
                        }
                    } else if (using.equalsIgnoreCase("iron_golem")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("irongolem.strength")) {
                            p.addPotionEffect(slow);
                            p.addPotionEffect(strength);
                        }
                    } else if (using.equalsIgnoreCase("rabbit")) {
						if (Config.MOB_CONFIG.getConfig().getBoolean("rabbit.jump-boost")) {
							p.addPotionEffect(jump);
						}
					} else if (using.equalsIgnoreCase("slime")) {
						if (Config.MOB_CONFIG.getConfig().getBoolean("slime.jump-boost")) {
							p.addPotionEffect(slimeJump);
						}
					} else if (using.equalsIgnoreCase("guardian")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("guardian.waterbreathing")) {
                            p.addPotionEffect(waterbreathing);
                            p.addPotionEffect(nightVision);
                        }
                    } else if (using.equalsIgnoreCase("bat")) {
                        p.addPotionEffect(nightVision);
                    } else if (using.equalsIgnoreCase("horse")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("horse.speed")) {
                            p.addPotionEffect(horseSpeed, true);
                        }
                    } else if (using.equalsIgnoreCase("skeleton_horse")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("horse.speed")) {
                            p.addPotionEffect(horseSpeed, true);
                        }
                    } else if (using.equalsIgnoreCase("ocelot")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("ocelot.speed")) {
                            p.addPotionEffect(ocelotSpeed, true);
                        }
                    } else if (using.equalsIgnoreCase("pig_zombie")) {
                        if (Config.MOB_CONFIG.getConfig().getBoolean("pig_zombie.speed")) {
                            p.addPotionEffect(zombieSpeed, true);
                        }
                    } else if (using.equalsIgnoreCase("giant")) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 2));
					} else if (using.equalsIgnoreCase("dolphin")) {
                        PotionEffect dolphinGrace = PotionEffectType.DOLPHINS_GRACE.createEffect(999999, 1);
                        p.addPotionEffect(dolphinGrace, true);
                    } else if (using.equalsIgnoreCase("drowned")) {
                        p.addPotionEffect(waterbreathing, true);

                        Block below = p.getLocation().subtract(0, 1, 0).getBlock();
                        Block in = p.getLocation().getBlock();

                        if (below.getType().isSolid() && in.getType() == Material.WATER) {
                            PotionEffect potion = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 2);
                            p.addPotionEffect(potion);
                        }
                    } else if (using.equalsIgnoreCase("cod") || using.equalsIgnoreCase("salmon")
                            || using.equalsIgnoreCase("pufferfish") || using.equalsIgnoreCase("tropicalfish")) {

                        Block in = p.getLocation().getBlock();
                        if (in.getType() == Material.WATER) {
                            PotionEffect potion = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 0);

                            p.addPotionEffect(waterbreathing, true);
                            p.addPotionEffect(potion);
                        }
                    } else if (using.equalsIgnoreCase("phantom")) {
                        p.addPotionEffect(nightVision);
                    } else if (using.equalsIgnoreCase("turtle")) {
                        p.addPotionEffect(waterbreathing);
                        p.addPotionEffect(dmgRes);
                        p.addPotionEffect(turtleSlow);
                    } else if (using.equalsIgnoreCase("strider")) {
                        Block b = p.getLocation().getBlock();
                        if (b.getType() == Material.LAVA) {
                            p.removePotionEffect(PotionEffectType.SLOW);
                            p.addPotionEffect(ocelotSpeed, true);
                            p.addPotionEffect(fireres, true);
                        } else {
                            p.addPotionEffect(squidSlow, true);
                            p.removePotionEffect(PotionEffectType.SPEED);
                            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        }
                    }
                }
			}
		}.runTaskTimer(pl, 10, 20);
	}

	@SuppressWarnings("deprecation")
    public static void burning(final Plugin pl) {
	    int damageDelay = 40;
//	    if (pl.getConfig().isSet("burningTickDelay")) {
//	        damageDelay = pl.getConfig().getInt("burningTickDelay");
//        }

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (DisguiseAPI.isDisguised(p)) {
                        if (!Morph.using.containsKey(p.getUniqueId()))
                            continue;
                        String using = morph.getUsing(p);

                        if (using.equalsIgnoreCase("snowman")) {
                            if (raining) {
                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        } else if (using.equalsIgnoreCase("stray")) {
                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        } else if (using.equalsIgnoreCase("skeleton")) {
                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        } else if (using.equalsIgnoreCase("zombie")) {
                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        } else if (using.equalsIgnoreCase("drowned")) {
                            if (p.getWorld().getTime() > 0 && p.getWorld().getTime() < 13000) {
                                if (!raining) {
                                    if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                        p.setFireTicks(60);
                                    }
                                }
                            }
                        } else if (using.equalsIgnoreCase("enderman")) {
                            if (raining) {
                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        } else if (using.equalsIgnoreCase("phantom")) {
                            if (p.getWorld().getTime() >= 0 && p.getWorld().getTime() < 13000) {
                                if (p.getLocation().getBlock().getLightFromSky() > 12) {
                                    p.setFireTicks(60);
                                }
                            }
                        } else if (using.equalsIgnoreCase("cod") || using.equalsIgnoreCase("salmon")
                                || using.equalsIgnoreCase("pufferfish") || using.equalsIgnoreCase("tropicalfish")) {

                            Block in = p.getLocation().getBlock();

                            if (in.getType() == Material.AIR) {

                                if (p.getHealth() - 0.5 <= 0) {
                                    p.setHealth(0);
                                } else {
                                    p.setHealth(p.getHealth() - 0.5);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(pl, damageDelay, damageDelay);
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
                    if (!Morph.using.containsKey(p.getUniqueId()))
                        continue;
                    if (!p.isSneaking())
                        continue;
                    if (MorphManager.soundDisabled.contains(p.getUniqueId()))
                        continue;

                    if (sounds.contains(p.getUniqueId())) {
                        Sound s = morph.playSound(p);
                        if (s != null)
                            p.getWorld().playSound(p.getLocation(), s, 2, 1);
                        sounds.remove(p.getUniqueId());
                    } else {
                        sounds.add(p.getUniqueId());
                    }
                }
			}
		}.runTaskTimer(Morph.pl, 0, 10);
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
                    if (!Morph.pl.getConfig().getBoolean("morph-power"))
                        continue;
                    if (MorphManager.morphTimeout.get(p.getUniqueId()) != null) {
                        if (MorphManager.morphTimeout.get(p.getUniqueId()) <= 30)
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
                            double take = Morph.pl.getConfig().getDouble("morphPower-use");
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
                        double give = Morph.pl.getConfig().getDouble("morphPower-regain");
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
        }.runTaskTimer(Morph.pl, 20, 20);
    }

    public static boolean hasFlyingAbility(Player p) {
        if (!Morph.using.containsKey(p.getUniqueId()))
            return false;
        switch (Morph.using.get(p.getUniqueId())) {
            case "bat":
                return true;
            case "vex":
                return true;
            case "wither":
                return true;
            case "blaze":
                return true;
            case "ghast":
                return true;
            case "ender_dragon":
                return true;
            case "parrot":
                return true;
        }
        return false;
    }

}