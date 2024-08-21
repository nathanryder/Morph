package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.DisguiseAPI;
import net.minecraft.server.v1_11_R1.PacketPlayOutCamera;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class InteractEvent implements Listener {

	MorphManager mm = new MorphManager();
	Messages m = new Messages();
    Random r = new Random();
	String prefix = m.getMessage("prefix");
	
	private HashMap<Player, Integer> skeletoncd;
	private HashMap<Player, Integer> endercd;
    private HashMap<Player, Integer> dragoncd;
	private HashMap<Player, Integer> ghastcd;
	private HashMap<Player, Integer> snowcd;
	private HashMap<Player, Integer> cowcd;
	private HashMap<Player, Integer> sheepcd;
    private HashMap<Player, Integer> evokerAttackcd;
    private HashMap<Player, Integer> evokerSpawncd;
    private HashMap<Player, Integer> blazecd;
    private HashMap<Player, Integer> vexcd;
    private HashMap<Player, Integer> chickencd;
	private HashMap<Player, Integer> llamacd;
	private HashMap<Player, Integer> spidercd;
	private HashMap<Player, Integer> straycd;
	private HashMap<Player, Integer> illusionercd;
	private HashMap<Player, BukkitRunnable> cdTask;
	
	Plugin pl = null;
	public InteractEvent(Plugin plugin) {
		pl = plugin;
		skeletoncd = new HashMap<>();
		endercd = new HashMap<>();
        dragoncd = new HashMap<>();
		ghastcd = new HashMap<>();
		snowcd = new HashMap<>();
		cowcd = new HashMap<>();
		sheepcd = new HashMap<>();
		evokerAttackcd = new HashMap<>();
        blazecd = new HashMap<>();
        vexcd = new HashMap<>();
		chickencd = new HashMap<>();
		llamacd = new HashMap<>();
		spidercd = new HashMap<>();
		straycd = new HashMap<>();
		evokerSpawncd = new HashMap<>();
		illusionercd = new HashMap<>();
		cdTask = new HashMap<>();
	}
	
	@EventHandler
    public void onClick(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
        if (!Morph.using.containsKey(p.getUniqueId()))
            return;
		String using = mm.getUsing(p);
		if (MorphManager.toggled.contains(p.getUniqueId()))
			return;

		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (using.equalsIgnoreCase("enderman")) {
				if (pl.getConfig().getString("enderman.teleport").equalsIgnoreCase("true")) {
					if (p.isSneaking()) {
						if (!(endercd.containsKey(p))) {
							float pitch = p.getLocation().getPitch();
							float yaw = p.getLocation().getYaw();
							Location tploc = p.getTargetBlock((Set<Material>) null, 100).getLocation();
							if (tploc == null)
								return;
							tploc.add(0, 1, 0);
							tploc.setPitch(pitch);
							tploc.setYaw(yaw);
							e.getPlayer().teleport(tploc);

							int cd = Morph.pl.getConfig().getInt("ender.ability-cooldown");
							if (cd != 0) {
								endercd.put(p, cd);
								cdTask.put(p, new BukkitRunnable() {

									public void run() {
										endercd.put(p, endercd.get(p) - 1);
										if (endercd.get(p) <= 1) {
											endercd.remove(p);
											cdTask.remove(p);
											cancel();
										}
									}

								});
								cdTask.get(p).runTaskTimer(pl, 20, 20);
							}
						} else {
							p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, endercd.get(p)));
						}
					}
				}
			} else if (using.equalsIgnoreCase("skeleton")) {
				if (pl.getConfig().getString("skeleton.shoot").equalsIgnoreCase("true")) {
					if (p.isSneaking()) {
						if (!(skeletoncd.containsKey(p))) {
							Arrow a = p.launchProjectile(Arrow.class);
							a.setMetadata("morph", new FixedMetadataValue(Morph.pl, "yes"));

							int cd = Morph.pl.getConfig().getInt("skeleton.ability-cooldown");
							if (cd != 0) {
								skeletoncd.put(p, cd);
								cdTask.put(p, new BukkitRunnable() {
									public void run() {
										skeletoncd.put(p, skeletoncd.get(p) - 1);
										if (skeletoncd.get(p) <= 1) {
											skeletoncd.remove(p);
											cdTask.remove(p);
											cancel();
										}
									}
								});
								cdTask.get(p).runTaskTimer(pl, 20, 20);
							}
						} else {
							p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, skeletoncd.get(p)));
						}
					}
				}
			} else if (using.equalsIgnoreCase("stray")) {
				if (pl.getConfig().getString("stray.shoot").equalsIgnoreCase("true")) {
					if (pl.getConfig().getString("stray.slow").equalsIgnoreCase("true")) {
						if (p.isSneaking()) {
							if (!(straycd.containsKey(p))) {
								Arrow a = p.launchProjectile(Arrow.class);
								a.setMetadata("morph", new FixedMetadataValue(Morph.pl, "yes:stray"));

								int cd = Morph.pl.getConfig().getInt("stray.ability-cooldown");
								if (cd != 0) {
									straycd.put(p, cd);
									cdTask.put(p, new BukkitRunnable() {
										public void run() {
											straycd.put(p, straycd.get(p) - 1);
											if (straycd.get(p) <= 1) {
												straycd.remove(p);
												cdTask.remove(p);
												cancel();
											}
										}
									});
									cdTask.get(p).runTaskTimer(pl, 20, 20);
								}
							} else {
								p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, straycd.get(p)));
							}
						}
					}
				}
			} else if (using.equalsIgnoreCase("snowman")) {
				if (pl.getConfig().getString("snowmen-shoot").equalsIgnoreCase("true")) {
					if (p.isSneaking()) {
						if (!(snowcd.containsKey(p))) {
							e.setCancelled(true);
							p.launchProjectile(Snowball.class);

							int cd = Morph.pl.getConfig().getInt("snowman.ability-cooldown");
							if (cd != 0) {
								snowcd.put(p, cd);
								cdTask.put(p, new BukkitRunnable() {
									public void run() {
										int cd = snowcd.get(p);
										snowcd.remove(p);
										snowcd.put(p, cd - 1);
										if (snowcd.get(p) <= 1) {
											snowcd.remove(p);
											cdTask.remove(p);
											cancel();
										}
									}

								});
								cdTask.get(p).runTaskTimer(pl, 20, 20);
							}
						} else {
							p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, snowcd.get(p)));
						}
					}
				}
			} else if (using.equalsIgnoreCase("ghast")) {
				if (pl.getConfig().getString("ghast.fireball").equalsIgnoreCase("true")) {
					if (p.isSneaking()) {
						if (!(ghastcd.containsKey(p))) {
							p.launchProjectile(Fireball.class);

							int cd = Morph.pl.getConfig().getInt("ghast.ability-cooldown");
							if (cd != 0) {
								ghastcd.put(p, cd);
								cdTask.put(p, new BukkitRunnable() {

									public void run() {
										ghastcd.put(p, ghastcd.get(p) - 1);
										if (ghastcd.get(p) <= 1) {
											ghastcd.remove(p);
											cdTask.remove(p);
											cancel();
										}
									}

								});
								cdTask.get(p).runTaskTimer(pl, 20, 20);
							}
						} else {
							p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, ghastcd.get(p)));
						}
					}
				}
			} else if (using.equalsIgnoreCase("sheep")) {
				if (p.getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {
					if (!(sheepcd.containsKey(p))) {
						ItemStack drop = new ItemStack(Material.WOOL);
						drop.setAmount(2);
						p.getWorld().dropItem(p.getLocation(), drop);

						sheepcd.put(p, 1800);
						cdTask.put(p, new BukkitRunnable() {

							public void run() {
								sheepcd.put(p, sheepcd.get(p) - 1);
								if (sheepcd.get(p) <= 1) {
									sheepcd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}

						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					} else {
						p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, sheepcd.get(p)));
					}
				} else {
					if (e.getClickedBlock() == null)
						return;
					String m = e.getClickedBlock().getType().toString();
					if (m.equals("GRASS")) {
						e.getClickedBlock().setType(Material.DIRT);
						int flevel = p.getFoodLevel();
						p.setFoodLevel(flevel + 2);
					}
				}
			} else if (using.equalsIgnoreCase("cow")) {
				if (p.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)) {
					if (!(cowcd.containsKey(p))) {
						ItemStack remove = new ItemStack(Material.BUCKET, 1);
						p.getInventory().removeItem(remove);
						ItemStack drop = new ItemStack(Material.MILK_BUCKET);
						drop.setAmount(1);
						p.getWorld().dropItem(p.getLocation(), drop);

						cowcd.put(p, 1800);
						cdTask.put(p, new BukkitRunnable() {

							public void run() {
								cowcd.put(p, cowcd.get(p) - 1);
								if (cowcd.get(p) <= 1) {
									cowcd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}

						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					} else {
						p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, cowcd.get(p)));
					}
				} else {
					if (e.getClickedBlock() != null) {
						String m = e.getClickedBlock().getType().toString();
						if (m.equals("GRASS")) {
							e.getClickedBlock().setType(Material.DIRT);
							int flevel = p.getFoodLevel();
							p.setFoodLevel(flevel + 2);
						}
					}
				}
			} else if (using.equalsIgnoreCase("blaze")) {
				if (pl.getConfig().getString("blaze.fire").equalsIgnoreCase("true")) {
					if (p.isSneaking()) {
						if (!(blazecd.containsKey(p))) {
							Fireball f = p.launchProjectile(Fireball.class);
							f.setFireTicks(0);

							int cd = Morph.pl.getConfig().getInt("blaze.ability-cooldown");
							if (cd != 0) {
								blazecd.put(p, cd);
								cdTask.put(p, new BukkitRunnable() {

									public void run() {
										blazecd.put(p, blazecd.get(p) - 1);
										if (blazecd.get(p) <= 1) {
											blazecd.remove(p);
											cdTask.remove(p);
											cancel();
										}
									}

								});
								cdTask.get(p).runTaskTimer(pl, 20, 20);
							}
						} else {
							p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, blazecd.get(p)));
						}
					}
				}
			} else if (using.equalsIgnoreCase("pig")) {
				if (e.getClickedBlock() == null)
					return;
				String m = e.getClickedBlock().getType().toString();
				if (m.equals("GRASS")) {
					e.getClickedBlock().setType(Material.DIRT);
					int flevel = p.getFoodLevel();
					p.setFoodLevel(flevel + 2);
				}
			} else if (using.equalsIgnoreCase("ender_dragon")) {
				if (!pl.getConfig().getBoolean("enderdragon.fireball"))
					return;
				if (p.isSneaking()) {
					if (!(dragoncd.containsKey(p))) {
						Fireball f = p.launchProjectile(DragonFireball.class);
						p.getWorld().playEffect(p.getLocation(), Effect.ENDERDRAGON_SHOOT, 5);
						f.setFireTicks(0);

						int cd = Morph.pl.getConfig().getInt("enderdragon.ability-cooldown");
						if (cd != 0) {
							dragoncd.put(p, cd);
							cdTask.put(p, new BukkitRunnable() {

								public void run() {
									dragoncd.put(p, dragoncd.get(p) - 1);
									if (dragoncd.get(p) <= 1) {
										dragoncd.remove(p);
										cdTask.remove(p);
										cancel();
									}
								}

							});
							cdTask.get(p).runTaskTimer(pl, 20, 20);
						}
					} else {
						p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, dragoncd.get(p)));
					}
				}
			} else if (using.equalsIgnoreCase("evoker")) {
				if (!pl.getConfig().getBoolean("evoker.attack"))
					return;
				if (!p.isSneaking())
					return;

				if (!(evokerAttackcd.containsKey(p))) {
					Location l = p.getLocation();
					BlockFace f = yawToFace(l.getYaw());
					for (int i = 1; i < 16; i++) {
						l = forward(f, l.clone());
						EvokerFangs a = (EvokerFangs) p.getWorld().spawnEntity(l, EntityType.EVOKER_FANGS);
						a.setOwner(p);
					}

					int cd = Morph.pl.getConfig().getInt("evoker.attack-cooldown");
					if (cd != 0) {
						evokerAttackcd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {

							public void run() {
								evokerAttackcd.put(p, evokerAttackcd.get(p) - 1);
								if (evokerAttackcd.get(p) <= 1) {
									evokerAttackcd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}

						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				} else {
					p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, evokerAttackcd.get(p)));
				}
			} else if (using.equalsIgnoreCase("vex")) {
				if (!pl.getConfig().getBoolean("vex.phase"))
					return;
				if (!p.isSneaking())
					return;

				if (!(vexcd.containsKey(p))) {
					Location l = p.getLocation();
					BlockFace f = yawToFace(p.getLocation().getYaw());
					Location inFront = forward(f, l.clone());
					if (!inFront.getBlock().getType().isSolid())
						return;
					if (inFront.getBlock().isLiquid())
						return;

					Location tp = null;
					int maxLayers = Morph.pl.getConfig().getInt("vex.max-layers");
					for (int i = 0; i <= maxLayers; i++) {
						if (tp != null)
							continue;

						l = forward(f, l.clone());
						if (l.getBlock().getType().isSolid())
							continue;
						if (l.getBlock().isLiquid())
							continue;
						tp = l;
					}
					if (tp == null) {
						p.sendMessage(m.getMessage("vexTooManyLayers"));
						return;
					}
					p.teleport(tp);

					int cd = Morph.pl.getConfig().getInt("vex.ability-cooldown");
					if (cd != 0) {
						vexcd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {
							public void run() {
								vexcd.put(p, vexcd.get(p) - 1);
								if (vexcd.get(p) <= 1) {
									vexcd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}
						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				} else {
					p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, vexcd.get(p)));
				}
			} else if (using.equalsIgnoreCase("chicken")) {
				if (!pl.getConfig().getBoolean("chicken.egg"))
					return;
				if (!p.isSneaking())
					return;

				if (!(chickencd.containsKey(p))) {
					p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(Material.EGG));

					int cd = Morph.pl.getConfig().getInt("chicken.ability-cooldown");
					if (cd != 0) {
						chickencd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {
							public void run() {
								chickencd.put(p, chickencd.get(p) - 1);
								if (chickencd.get(p) <= 1) {
									chickencd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}
						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				} else {
					p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, chickencd.get(p)));
				}
			} else if (using.equalsIgnoreCase("creeper")) {
				if (!pl.getConfig().getBoolean("creeper.explosion"))
					return;
				if (!p.isSneaking())
					return;

				p.setHealth(0);
			} else if (using.equalsIgnoreCase("llama")) {
				if (!pl.getConfig().getBoolean("llama.spit"))
					return;
				if (!p.isSneaking())
					return;

				if (!(llamacd.containsKey(p))) {
					LlamaSpit s = (LlamaSpit) p.getWorld().spawnEntity(p.getLocation().add(0, 1.5, 0), EntityType.LLAMA_SPIT);
					s.setShooter(p);
					s.setVelocity(p.getLocation().getDirection());

					int cd = Morph.pl.getConfig().getInt("llama.ability-cooldown");
					if (cd != 0) {
						llamacd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {
							public void run() {
								llamacd.put(p, llamacd.get(p) - 1);
								if (llamacd.get(p) <= 1) {
									llamacd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}
						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				} else {
					p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, llamacd.get(p)));
				}
			} else if (using.equalsIgnoreCase("spider")) {
				if (!pl.getConfig().getBoolean("spider.web"))
					return;
				if (!p.isSneaking())
					return;

				if (!(spidercd.containsKey(p))) {
					final FallingBlock b = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.WEB));
					b.setVelocity(p.getEyeLocation().getDirection().multiply(2));

					if (Morph.pl.getConfig().getBoolean("spider.removeSpiderWeb")) {
						int time = Morph.pl.getConfig().getInt("spider.spiderWebRemove");
						Bukkit.getServer().getScheduler().runTaskLater(Morph.pl, new Runnable() {
							@Override
							public void run() {
								b.remove();
								if (b.getLocation().getBlock().getType() == Material.WEB)
									b.getLocation().getBlock().setType(Material.AIR);
							}
						}, 20 * time);
					}

					int cd = Morph.pl.getConfig().getInt("spider.ability-cooldown");
					if (cd != 0) {
						spidercd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {
							public void run() {
								spidercd.put(p, spidercd.get(p) - 1);
								if (spidercd.get(p) <= 1) {
									spidercd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}
						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				} else {
					p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, spidercd.get(p)));
				}
			} else {
				p.sendMessage(prefix + " " + m.getMessage("cooldown", "", p.getDisplayName(), using, evokerSpawncd.get(p)));
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (using.equalsIgnoreCase("evoker")) {
				if (!pl.getConfig().getBoolean("evoker.spawnVex"))
					return;
				if (!p.isSneaking())
					return;

				if (!(evokerSpawncd.containsKey(p))) {
					int c = r.nextInt(3) + 2;
					for (int i = 0; i <= c; i++) {
						p.getWorld().spawnEntity(p.getLocation().add(0, 1, 0), EntityType.VEX);
					}

					int cd = Morph.pl.getConfig().getInt("evoker.spawnVex-cooldown");
					if (cd != 0) {
						evokerSpawncd.put(p, cd);
						cdTask.put(p, new BukkitRunnable() {
							public void run() {
								evokerSpawncd.put(p, evokerSpawncd.get(p) - 1);
								if (evokerSpawncd.get(p) <= 1) {
									evokerSpawncd.remove(p);
									cdTask.remove(p);
									cancel();
								}
							}
						});
						cdTask.get(p).runTaskTimer(pl, 20, 20);
					}
				}
			}
		}
	}

    public Location forward(BlockFace f, Location loc) {
        Location l = null;
        switch (f) {
            case NORTH:
                l = loc.add(0,0,-1);
                break;
            case SOUTH:
                l = loc.add(0,0,1);
                break;
            case EAST:
                l = loc.add(1,0,0);
                break;
            case WEST:
                l = loc.add(-1,0,0);
                break;
        }
        return l;
    }

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    public BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }
}
