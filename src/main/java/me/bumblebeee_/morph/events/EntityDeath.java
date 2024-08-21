package me.bumblebeee_.morph.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.bumblebeee_.morph.Morph;
import me.libraryaddict.disguise.disguisetypes.RabbitType;
import me.libraryaddict.disguise.disguisetypes.watchers.RabbitWatcher;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

public class EntityDeath implements Listener {
	
	Plugin pl = null;
	public EntityDeath(Plugin plugin) {
		pl = plugin;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent ev) {
		Player killer = ev.getEntity().getKiller();
		if (killer != null && killer instanceof Player) {
			if (pl.getConfig().getList("enabled-worlds").contains(killer.getWorld().getName()) || pl.getConfig().getList("enabled-worlds").contains("<all>")) {
				File userFile = new File(pl.getDataFolder() + "/UserData/" + killer.getUniqueId() + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				List<String> stringList = fileConfig.getStringList("Mobs");
				List<String> players = fileConfig.getStringList("Players");

				System.out.println(ev.getEntity().toString().toLowerCase());
				if (pl.getConfig().getList("enabled-worlds").contains(killer.getWorld().getName()) || pl.getConfig().getList("enabled-worlds").contains("<all>")) {
					if (ev.getEntity().toString().toLowerCase().startsWith("crafthorse{variant=skeleton_horse")) {
						if (killer.hasPermission("morph.into.skeleton_horse")) {
							String t = "skeleton_horse";
							if (!stringList.contains(t)) {
								stringList.add(t);
								fileConfig.set("Mobs", stringList);
								try {
									fileConfig.save(userFile);
								} catch (IOException e) {
									e.printStackTrace();
								}
								killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a skeleton horse!");
							}
						}
					} else if (ev.getEntity().toString().toLowerCase().contains("crafthorse")) {
						 if (killer.hasPermission("morph.into.horse")) {
							 Horse h = (Horse) ev.getEntity();
							 if (h.isAdult()) {
								 if (!stringList.contains("horse")) {
									 stringList.add("horse");
									 fileConfig.set("Mobs", stringList);
									 try {
										 fileConfig.save(userFile);
									 } catch (IOException e) {
										 e.printStackTrace();
									 }
									 killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a horse!");
								 }
							 } else {
								 if (!stringList.contains("horse:baby")) {
									 stringList.add("horse:baby");
									 fileConfig.set("Mobs", stringList);
									 try {
										 fileConfig.save(userFile);
									 } catch (IOException e) {
										 e.printStackTrace();
									 }
									 killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby horse!");
								 }
							 }
						}
					} else if (ev.getEntity().toString().toLowerCase().contains("craftocelot")) {
						if (!killer.hasPermission("morph.bypasskill.ocelot")) {
							Ocelot o = (Ocelot) ev.getEntity();
							if (killer.hasPermission("morph.into.ocelot")) {
								if (o.isAdult()) {
									if (!stringList.contains("ocelot")) {
										stringList.add("ocelot");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a ocelot!");
									}
								} else {
									if (!stringList.contains("ocelot:baby")) {
										stringList.add("ocelot:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby ocelot!");
									}
								}
							}
						}
					} else if (ev.getEntity().toString().toLowerCase().contains("craftwolf")) {
						if (!killer.hasPermission("morph.bypasskill.wolf")) {
							if (killer.hasPermission("morph.into.wolf")) {
								Wolf w = (Wolf) ev.getEntity();
								if (w.isAdult()) {
									if (!stringList.contains("wolf")) {
										if (!stringList.contains("wolf")) {
											stringList.add("wolf");
											fileConfig.set("Mobs", stringList);
											try {
												fileConfig.save(userFile);
											} catch (IOException e) {
												e.printStackTrace();
											}
											killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a wolf!");
										}
									}
								} else {
									if (!stringList.contains("wolf:baby")) {
										if (!stringList.contains("wolf:baby")) {
											stringList.add("wolf:baby");
											fileConfig.set("Mobs", stringList);
											try {
												fileConfig.save(userFile);
											} catch (IOException e) {
												e.printStackTrace();
											}
											killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby wolf!");
										}
									}
								}
							}
						}
					} else if (ev.getEntity().toString().toLowerCase().contains("craftplayer")) {
						if (pl.getConfig().getString("enable-players") == "true") {
							if (killer.hasPermission("morph.into.player")) {
								String t = ev.getEntity().getName();
								if (!players.toString().contains(t)) {
									players.add(t);
									fileConfig.set("Players", players);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into " + t + "!");
								}
							}
						}
					}
				}

				if (pl.getConfig().getList("enabled-worlds").contains(killer.getWorld().getName()) || pl.getConfig().getList("enabled-worlds").contains("<all>")) {
					switch (ev.getEntity().toString().toLowerCase()) {
					case "craftpig":
						if (!killer.hasPermission("morph.bypasskill.pig")) {
							if (killer.hasPermission("morph.into.pig")) {
								Pig p = (Pig) ev.getEntity();
								if (p.isAdult()) {
									if (!stringList.contains("pig")) {
										stringList.add("pig");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a pig!");
										break;
									}
								} else {
									if (!stringList.contains("pig:baby")) {
										stringList.add("pig:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby pig!");
										break;
									}
								}
							}
						}
						break;
					case "craftcow":
						if (!killer.hasPermission("morph.bypasskill.cow")) {
							if (killer.hasPermission("morph.into.cow")) {
								Cow c = (Cow) ev.getEntity();
								if (c.isAdult()) {
									if (!stringList.contains("cow")) {
										stringList.add("cow");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a cow!");
										break;
									}
								} else {
									if (!stringList.contains("cow:baby")) {
										stringList.add("cow:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby cow!");
										break;
									}
								}
							}
						}
						break;
					case "craftwitherskeleton":
						if (!killer.hasPermission("morph.bypasskill.wither_skeleton")) {
							if (killer.hasPermission("morph.into.wither_skeleton")) {
								if (!stringList.contains("wither_skeleton")) {
									stringList.add("wither_skeleton");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a wither_skeleton!");
									break;
								}
							}
						}
						break;
					case "craftbat":
						if (!killer.hasPermission("morph.bypasskill.bat")) {
							if (killer.hasPermission("morph.into.bat")) {
								if (!stringList.contains("bat")) {
									stringList.add("bat");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a bat!");
									break;
								}
							}
						}
						break;
					case "craftblaze":
						if (!killer.hasPermission("morph.bypasskill.blaze")) {
							if (killer.hasPermission("morph.into.blaze")) {
								if (!stringList.contains("blaze")) {
									stringList.add("blaze");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a blaze!");
									break;
								}
							}
						}
						break;
					case "craftcavespider":
						if (!killer.hasPermission("morph.bypasskill.cave_spider")) {
							if (killer.hasPermission("morph.into.cave_spider")) {
								if (!stringList.contains("cave_spider")) {
									stringList.add("cave_spider");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a cavespider!");
									break;
								}
							}
						}
						break;
					case "craftchicken":
						if (!killer.hasPermission("morph.bypasskill.chicken")) {
							if (killer.hasPermission("morph.into.chicken")) {
								Chicken c = (Chicken) ev.getEntity();
								if (c.isAdult()) {
									if (!stringList.contains("chicken")) {
										stringList.add("chicken");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a chicken!");
										break;
									}
								} else {
									if (!stringList.contains("chicken:baby")) {
										stringList.add("chicken:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby chicken!");
										break;
									}
								}
							}
						}
						break;
					case "craftcreeper":
						if (!killer.hasPermission("morph.bypasskill.creeper")) {
							if (killer.hasPermission("morph.into.creeper")) {
								if (!stringList.contains("creeper")) {
									stringList.add("creeper");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a creeper!");
									break;
								}
							}
						}
						break;
					case "craftenderman":
						if (!killer.hasPermission("morph.bypasskill.enderman")) {
							if (killer.hasPermission("morph.into.enderman")) {
								if (!stringList.contains("enderman")) {
									stringList.add("enderman");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a enderman!");
									break;
								}
							}
						}
						break;
					case "craftendermite":
						if (!killer.hasPermission("morph.bypasskill.endermite")) {
							if (killer.hasPermission("morph.into.endermite")) {
								if (!stringList.contains("endermite")) {
									stringList.add("endermite");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a endermite!");
									break;
								}
							}
						}
						break;
					case "craftghast":
						if (!killer.hasPermission("morph.bypasskill.ghast")) {
							if (killer.hasPermission("morph.into.ghast")) {
								if (!stringList.contains("ghast")) {
									stringList.add("ghast");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a ghast!");
									break;
								}
							}
						}
						break;
					case "craftguardian":
						if (!killer.hasPermission("morph.bypasskill.guardian")) {
							if (killer.hasPermission("morph.into.guardian")) {
								if (!stringList.contains("guardian")) {
									stringList.add("guardian");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a guardian!");
									break;
								}
							}
						}
						break;
					case "craftirongolem":
						if (!killer.hasPermission("morph.bypasskill.iron_golem")) {
							if (killer.hasPermission("morph.into.iron_golem")) {
								if (!stringList.contains("iron_golem")) {
									stringList.add("iron_golem");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a irongolem!");
									break;
								}
							}
						}
						break;
					case "craftmagmacube":
						if (!killer.hasPermission("morph.bypasskill.magma_cube")) {
							if (killer.hasPermission("morph.into.magma_cube")) {
								if (!stringList.contains("magma_cube")) {
									stringList.add("magma_cube");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a magmacube!");
									break;
								}
							}
						}
						break;
					case "craftmushroomcow":
						if (!killer.hasPermission("morph.bypasskill.mushroom_cow")) {
							if (killer.hasPermission("morph.into.mushroom_cow")) {
								MushroomCow c = (MushroomCow) ev.getEntity();
								if (c.isAdult()) {
									if (!stringList.contains("mushroom_cow")) {
										stringList.add("mushroom_cow");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a mushroomcow!");
										break;
									}
								} else {
									if (!stringList.contains("mushroom_cow:baby")) {
										stringList.add("mushroom_cow:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby mushroomcow!");
										break;
									}
								}
							}
						}
						break;
					case "craftmule":
						if (!killer.hasPermission("morph.bypasskill.mule")) {
							if (killer.hasPermission("morph.into.mule")) {
								Mule c = (Mule) ev.getEntity();
								if (c.isAdult()) {
									if (!stringList.contains("mule")) {
										stringList.add("mule");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a mule!");
										break;
									}
								} else {
									if (!stringList.contains("mule:baby")) {
										stringList.add("mule:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby mule!");
										break;
									}
								}
							}
						}
						break;
					case "craftpigzombie":
						if (!killer.hasPermission("morph.bypasskill.pig_zombie")) {
							if (killer.hasPermission("morph.into.pig_zombie")) {
								PigZombie p = (PigZombie) ev.getEntity();
								if (p.isBaby()) {
									if (!stringList.contains("pig_zombie:baby")) {
										stringList.add("pig_zombie:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby zombie pigman!");
										break;
									}
								} else {
									if (!stringList.contains("pig_zombie")) {
										stringList.add("pig_zombie");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a zombie pigman!");
										break;
									}
								}
							}
						}
						break;
					case "craftvillagerzombie":
						if (!killer.hasPermission("morph.bypasskill.zombie_villager")) {
							if (killer.hasPermission("morph.into.zombie_villager")) {
								ZombieVillager p = (ZombieVillager) ev.getEntity();
								if (p.isBaby()) {
									if (!stringList.contains("zombie_villager:baby")) {
										stringList.add("zombie_villager:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby zombie villager!");
										break;
									}
								} else {
									if (!stringList.contains("zombie_villager")) {
										stringList.add("zombie_villager");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a zombie villager!");
										break;
									}
								}
							}
						}
						break;
					case "craftsheep":
						if (!killer.hasPermission("morph.bypasskill.sheep")) {
							if (killer.hasPermission("morph.into.sheep")) {
								Sheep s = (Sheep) ev.getEntity();
								if (s.isAdult()) {
									if (!stringList.contains("sheep")) {
										stringList.add("sheep");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a sheep!");
										break;
									}
								} else {
									if (!stringList.contains("sheep:baby")) {
										stringList.add("sheep:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby sheep!");
										break;
									}
								}
							}
						}
						break;
					case "craftsilverfish":
						if (!killer.hasPermission("morph.bypasskill.silverfish")) {
							if (killer.hasPermission("morph.into.silverfish")) {
								if (!stringList.contains("silverfish")) {
									stringList.add("silverfish");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a silverfish!");
									break;
								}
							}
						}
						break;
					case "craftskeleton":
						Skeleton k = (Skeleton) ev.getEntity();
						if (k.getSkeletonType() == Skeleton.SkeletonType.STRAY) {
							if (!killer.hasPermission("morph.bypasskill.stray")) {
								if (killer.hasPermission("morph.into.stray")) {
									if (!stringList.contains("stray")) {
										stringList.add("stray");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Stray!");
										break;
									}
								}
							}
						} else {
							if (!killer.hasPermission("morph.bypasskill.skeleton")) {
								if (killer.hasPermission("morph.into.skeleton")) {
									if (!stringList.contains("skeleton")) {
										stringList.add("skeleton");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a skeleton!");
										break;
									}
								}
							}
						}
						break;
					case "craftslime":
						if (!killer.hasPermission("morph.bypasskill.slime")) {
							if (killer.hasPermission("morph.into.slime")) {
								if (!stringList.contains("slime")) {
									stringList.add("slime");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a slime!");
									break;
								}
							}
						}
						break;
					case "craftsnowman":
						if (!killer.hasPermission("morph.bypasskill.snowman")) {
							if (killer.hasPermission("morph.into.snowman")) {
								if (!stringList.contains("snowman")) {
									stringList.add("snowman");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a snowman!");
									break;
								}
							}
						}
						break;
					case "craftspider":
						if (!killer.hasPermission("morph.bypasskill.spider")) {
							if (killer.hasPermission("morph.into.spider")) {
								if (!stringList.contains("spider")) {
									stringList.add("spider");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a spider!");
									break;
								}
							}
						}
						break;
					case "craftsquid":
						if (!killer.hasPermission("morph.bypasskill.squid")) {
							if (killer.hasPermission("morph.into.squid")) {
								if (!stringList.contains("squid")) {
									stringList.add("squid");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a squid!");
									break;
								}
							}
						}
						break;
					case "craftvillager":
						if (!killer.hasPermission("morph.bypasskill.villager")) {
							if (killer.hasPermission("morph.into.villager")) {
								Villager v = (Villager) ev.getEntity();
								if (v.isAdult()) {
									if (!stringList.contains("villager")) {
										stringList.add("villager");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a villager!");
										break;
									}
								} else {
									if (!stringList.contains("villager:baby")) {
										stringList.add("villager:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby villager!");
										break;
									}
								}
							}
						}
						break;
					case "craftwitch":
						if (!killer.hasPermission("morph.bypasskill.witch")) {
							if (killer.hasPermission("morph.into.witch")) {
								if (!stringList.contains("witch")) {
									stringList.add("witch");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a witch!");
									break;
								}
							}
						}
						break;
					case "craftwither":
						if (!killer.hasPermission("morph.bypasskill.wither")) {
							if (killer.hasPermission("morph.into.wither")) {
								if (!stringList.contains("wither")) {
									stringList.add("wither");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a wither!");
									break;
								}
							}
						}
						break;
					case "craftzombie":
						if (!killer.hasPermission("morph.bypasskill.zombie")) {
							if (killer.hasPermission("morph.into.zombie")) {
								Zombie z = (Zombie) ev.getEntity();
								if (z.isBaby()) {
									if (!stringList.contains("zombie:baby")) {
										stringList.add("zombie:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby zombie!");
										break;
									}
								} else {
									if (!stringList.contains("zombie")) {
										stringList.add("zombie");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a zombie!");
										break;
									}
								}
							}
						}
						break;
					case "crafthusk":
						Husk z = (Husk) ev.getEntity();
						if (!killer.hasPermission("morph.bypasskill.husk")) {
							if (killer.hasPermission("morph.into.husk")) {
								if (z.isBaby()) {
									if (!stringList.contains("husk:baby")) {
										stringList.add("husk:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby husk!");
										break;
									}
								} else {
									if (!stringList.contains("husk")) {
										stringList.add("husk");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a husk!");
										break;
									}
								}
							}
						}
						break;
                    case "craftgolem":
                        if (!killer.hasPermission("morph.bypasskill.shulker")) {
                            if (killer.hasPermission("morph.into.shulker")) {
                                if (!stringList.contains("shulker")) {
                                    stringList.add("shulker");
                                    fileConfig.set("Mobs", stringList);
                                    try {
                                        fileConfig.save(userFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a shulker!");
                                    break;
                                }
                            }
                        }
                        break;
                    case "craftpolarbear":
                        if (!killer.hasPermission("morph.bypasskill.polarbear")) {
                            if (killer.hasPermission("morph.into.polar_bear")) {
								PolarBear p = (PolarBear) ev.getEntity();
								if (p.isAdult()) {
									if (!stringList.contains("polar_bear")) {
										stringList.add("polar_bear");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Polar Bear!");
										break;
									}
								} else {
									if (!stringList.contains("polar_bear:baby")) {
										stringList.add("polar_bear:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby Polar Bear!");
										break;
									}
								}
                            }
                        }
					break;
					case "craftllama":
						if (!killer.hasPermission("morph.bypasskill.llama")) {
							if (killer.hasPermission("morph.into.llama")) {
								Llama l = (Llama) ev.getEntity();
								if (l.isAdult()) {
									if (!stringList.contains("llama")) {
										stringList.add("llama");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Llama!");
										break;
									}
								} else {
									if (!stringList.contains("llama:baby")) {
										stringList.add("llama:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby Llama!");
										break;
									}
								}
							}
						}
						break;
					case "craftvindicator":
						if (!killer.hasPermission("morph.bypasskill.vindicator")) {
							if (killer.hasPermission("morph.into.vindicator")) {
								if (!stringList.contains("vindicator")) {
									stringList.add("vindicator");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Vindicator!");
									break;
								}
							}
						}
						break;
					case "craftevoker":
						if (!killer.hasPermission("morph.bypasskill.evoker")) {
							if (killer.hasPermission("morph.into.evoker")) {
								if (!stringList.contains("evoker")) {
									stringList.add("evoker");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Evoker!");
									break;
								}
							}
						}
						break;
					case "craftvex":
						if (!killer.hasPermission("morph.bypasskill.vex")) {
							if (killer.hasPermission("morph.into.vex")) {
								if (!stringList.contains("vex")) {
									stringList.add("vex");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Vex!");
									break;
								}
							}
						}
						break;
					case "craftgiant":
                        if (!Morph.pl.getConfig().getBoolean("giant.enabled"))
                            break;
                        if (!killer.hasPermission("morph.bypasskill.giant")) {
                            if (killer.hasPermission("morph.into.giant")) {
                                if (!stringList.contains("giant")) {
                                    stringList.add("giant");
                                    fileConfig.set("Mobs", stringList);
                                    try {
                                        fileConfig.save(userFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a Giant!");
                                    break;
                                }
                            }
                        }
                        break;
					case "craftenderdragon":
                        if (!Morph.pl.getConfig().getBoolean("enderdragon.enabled"))
                            break;
                        if (!killer.hasPermission("morph.bypasskill.enderdragon")) {
                            if (killer.hasPermission("morph.into.enderdragon")) {
                                if (!stringList.contains("enderdragon")) {
                                    stringList.add("ender_dragon");
                                    fileConfig.set("Mobs", stringList);
                                    try {
                                        fileConfig.save(userFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into an Enderdragon!");
                                    break;
                                }
                            }
                        }
						break;
					case "craftdonkey":
						if (!killer.hasPermission("morph.bypasskill.donkey")) {
							if (killer.hasPermission("morph.into.donkey")) {
								Donkey c = (Donkey) ev.getEntity();
								if (c.isAdult()) {
									if (!stringList.contains("donkey")) {
										stringList.add("donkey");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a donkey!");
										break;
									}
								} else {
									if (!stringList.contains("donkey:baby")) {
										stringList.add("donkey:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby donkey!");
										break;
									}
								}
							}
						}
						break;
					case "craftillusioner":
						if (!killer.hasPermission("morph.bypasskill.illusioner")) {
							if (killer.hasPermission("morph.into.illusioner")) {
								if (!stringList.contains("illusioner")) {
									stringList.add("illusioner");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a illusioner!");
									break;
								}
							}
						}
						break;
					case "craftparrot":
						if (!killer.hasPermission("morph.bypasskill.parrot")) {
							if (killer.hasPermission("morph.into.parrot")) {
								if (!stringList.contains("parrot")) {
									stringList.add("parrot");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a parrot!");
									break;
								}
							}
						}
						break;
					case "craftdolphin":
						if (!killer.hasPermission("morph.bypasskill.dolphin")) {
							if (killer.hasPermission("morph.into.dolphin")) {
								if (!stringList.contains("dolphin")) {
									stringList.add("dolphin");
									fileConfig.set("Mobs", stringList);
									try {
										fileConfig.save(userFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a dolphin!");
									break;
								}
							}
						}
						break;
                    case "craftrabbit{rabbittype=black_and_white}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=brown}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=gold}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=salt_and_pepper}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=white}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
                                if (!stringList.contains("rabbit")) {
                                    stringList.add("rabbit");
                                    fileConfig.set("Mobs", stringList);
                                    try {
                                        fileConfig.save(userFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a !");
                                    break;
                                }
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=black}":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    case "craftrabbit{rabbittype=the_killer_bunny":
                        if (!killer.hasPermission("morph.bypasskill.rabbit")) {
                            if (killer.hasPermission("morph.into.rabbit")) {
								Rabbit r = (Rabbit) ev.getEntity();
								if (r.isAdult()) {
									if (!stringList.contains("rabbit")) {
										stringList.add("rabbit");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a rabbit!");
										break;
									}
								} else {
									if (!stringList.contains("rabbit:baby")) {
										stringList.add("rabbit:baby");
										fileConfig.set("Mobs", stringList);
										try {
											fileConfig.save(userFile);
										} catch (IOException e) {
											e.printStackTrace();
										}
										killer.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.YELLOW + "You can now morph into a baby rabbit!");
										break;
									}
								}
                            }
                        }
                        break;
                    }
                }
			}
		}
	}
}