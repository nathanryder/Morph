package me.bumblebeee_.morph.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
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
	Messages msgs = new Messages();
	MorphManager morph = new MorphManager();

	public EntityDeath(Plugin plugin) {
		pl = plugin;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent ev) {
		String prefix = msgs.getMessage("prefix");
		Player killer = ev.getEntity().getKiller();

		if (killer != null && killer instanceof Player) {
			if (pl.getConfig().getList("enabled-worlds").contains(killer.getWorld().getName()) || pl.getConfig().getList("enabled-worlds").contains("<all>")) {
				File userFile = new File(pl.getDataFolder() + "/UserData/" + killer.getUniqueId() + ".yml");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
				List<String> stringList = fileConfig.getStringList("Mobs");
				List<String> players = fileConfig.getStringList("Players");

				String fullType = null;
				if (ev.getEntity().toString().toLowerCase().startsWith("crafthorse{variant=skeleton_horse")) {
					if (killer.hasPermission("morph.into.skeleton_horse")) {
						fullType = "skeleton_horse";
					}
				} else if (ev.getEntity().toString().toLowerCase().contains("crafthorse")) {
					Horse h = (Horse) ev.getEntity();
					if (h.isAdult()) {
						fullType = "horse";
					} else {
						fullType = "horse:baby";
					}
				} else if (ev.getEntity().toString().toLowerCase().contains("craftocelot")) {
					Ocelot o = (Ocelot) ev.getEntity();
					if (o.isAdult()) {
						fullType = "ocelot";
					} else {
						fullType = "ocelot:baby";
					}
				} else if (ev.getEntity().toString().toLowerCase().contains("craftwolf")) {
					Wolf w = (Wolf) ev.getEntity();
					if (w.isAdult()) {
						fullType = "wolf";
					} else {
						fullType = "wolf:baby";
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
								String msg = msgs.getMessage("youCanNowMorph", t, "", "");
								killer.sendMessage(prefix + " " + msg);
							}
						}
					}
				}

				System.out.println("Type: " + ev.getEntity().toString().toLowerCase());
				switch (ev.getEntity().toString().toLowerCase()) {
					case "craftpig":
						Pig p = (Pig) ev.getEntity();
						if (p.isAdult()) {
							fullType = "pig";
						} else {
							fullType = "pig:baby";
						}
						break;
					case "craftcow":
						Cow c = (Cow) ev.getEntity();
						if (c.isAdult()) {
							fullType = "cow";
						} else {
							fullType = "cow:baby";
						}
						break;
					case "craftwitherskeleton":
						fullType = "wither_skeleton";
						break;
					case "craftbat":
						fullType = "bat";
						break;
					case "craftblaze":
						fullType = "blaze";
						break;
					case "craftcavespider":
						fullType = "cave_spider";
						break;
					case "craftchicken":
						Chicken ch = (Chicken) ev.getEntity();
						if (ch.isAdult()) {
							fullType = "chicken";
						} else {
							fullType = "chicken:baby";
						}
						break;
					case "craftcreeper":
						fullType = "creeper";
						break;
					case "craftenderman":
						fullType = "enderman";
						break;
					case "craftendermite":
						fullType = "endermite";
						break;
					case "craftghast":
						fullType = "ghast";
						break;
					case "craftguardian":
						fullType = "guardian";
						break;
					case "craftirongolem":
						fullType = "iron_golem";
						break;
					case "craftmagmacube":
						fullType = "magma_cube";
						break;
					case "craftmushroomcow":
						MushroomCow mc = (MushroomCow) ev.getEntity();
						if (mc.isAdult()) {
							fullType = "mushroom_cow";
						} else {
							fullType = "mushroom_cow:baby";
						}
						break;
					case "craftmule":
						Mule m = (Mule) ev.getEntity();
						if (m.isAdult()) {
							fullType = "mule";
						} else {
							fullType = "mule:baby";
						}
						break;
					case "craftpigzombie":
						System.out.println("1");
						PigZombie pz = (PigZombie) ev.getEntity();
						if (pz.isBaby()) {
							fullType = "zombified_piglin:baby";
						} else {
							System.out.println("2");
							fullType = "zombified_piglin";
						}
						break;
					case "craftvillagerzombie":
						ZombieVillager zv = (ZombieVillager) ev.getEntity();
						if (zv.isBaby()) {
							fullType = "zombie_villager:baby";
						} else {
							fullType = "zombie_villager";
						}
						break;
					case "craftsheep":
						Sheep s = (Sheep) ev.getEntity();
						if (s.isAdult()) {
							fullType = "sheep";
						} else {
							fullType = "sheep:baby";
						}
						break;
					case "craftsilverfish":
						fullType = "silverfish";
						break;
					case "craftskeleton":
						Skeleton k = (Skeleton) ev.getEntity();
						if (k.getSkeletonType() == Skeleton.SkeletonType.STRAY) {
							fullType = "stray";
						} else {
							fullType = "skeleton";
						}
						break;
					case "craftslime":
						fullType = "slime";
						break;
					case "craftsnowman":
						fullType = "snowman";
						break;
					case "craftspider":
						fullType = "spider";
						break;
					case "craftsquid":
						fullType = "squid";
						break;
					case "craftvillager":
						Villager v = (Villager) ev.getEntity();
						if (v.isAdult()) {
							fullType = "villager";
						} else {
							fullType = "villager:baby";
						}
						break;
					case "craftwitch":
						fullType = "witch";
						break;
					case "craftwither":
						fullType = "wither";
						break;
					case "craftzombie":
						Zombie z = (Zombie) ev.getEntity();
						if (z.isBaby()) {
							fullType = "zombie:baby";
						} else {
							fullType = "zombie";
						}
						break;
					case "crafthusk":
						Husk h = (Husk) ev.getEntity();
						if (h.isBaby()) {
							fullType = "husk:baby";
						} else {
							fullType = "husk";
						}
						break;
					case "craftgolem":
						fullType = "shulker";
						break;
					case "craftpolarbear":
						PolarBear pb = (PolarBear) ev.getEntity();
						if (pb.isAdult()) {
							fullType = "polar_bear";
						} else {
							fullType = "polar_bear:baby";
						}
						break;
					case "craftllama":
						Llama l = (Llama) ev.getEntity();
						if (l.isAdult()) {
							fullType = "llama";
						} else {
							fullType = "llama:baby";
						}
						break;
					case "craftvindicator":
						fullType = "vindicator";
						break;
					case "craftevoker":
						fullType = "evoker";
						break;
					case "craftvex":
						fullType = "vex";
						break;
					case "craftgiant":
						fullType = "giant";
						break;
					case "craftenderdragon":
						fullType = "enderdragon";
						break;
					case "craftdonkey":
						Donkey d = (Donkey) ev.getEntity();
						if (d.isAdult()) {
							fullType = "donkey";
						} else {
							fullType = "donkey:baby";
						}
						break;
					case "craftillusioner":
						fullType = "illusioner";
						break;
					case "craftparrot":
						fullType = "parrot";
						break;
					case "craftdolphin":
						fullType = "dolphin";
						break;
					case "craftdrowned":
						fullType = "drowned";
						break;
					case "craftcod":
						fullType = "cod";
						break;
					case "craftsalmon":
						fullType = "salmon";
						break;
					case "craftpufferfish":
						fileConfig.set("Mobs", stringList);
						break;
					case "crafttropicalfish":
						fullType = "tropicalfish";
						break;
					case "craftphantom":
						fullType = "phantom";
						break;
					case "craftturtle":
						fullType = "turtle";
						break;
					case "craftbee":
						fullType = "bee";
						break;
					case "craftrabbit{rabbittype=black_and_white}":
					case "craftrabbit{rabbittype=brown}":
					case "craftrabbit{rabbittype=gold}":
					case "craftrabbit{rabbittype=salt_and_pepper}":
					case "craftrabbit{rabbittype=white}":
					case "craftrabbit{rabbittype=black}":
					case "craftrabbit{rabbittype=the_killer_bunny":
						Rabbit r = (Rabbit) ev.getEntity();
						if (r.isAdult()) {
							fullType = "rabbit";
						} else {
							fullType = "rabbit:baby";
						}
						break;
					case "craftcat":
						fullType = "cat";
						break;
					case "craftfox":
						fullType = "fox";
						break;
					case "craftpanda":
						fullType = "panda";
						break;
					case "crafthoglin":
						fullType = "hoglin";
						break;
					case "craftstrider":
						fullType = "strider";
						break;
					case "craftzoglin":
						fullType = "zoglin";
						break;
					case "craftpillager":
						fullType = "pillager";
						break;
					case "craftpiglinbrute":
						fullType = "piglinbrute";
						break;
					case "craftpiglin":
						fullType = "piglin";
						break;
					case "craftravager":
						fullType = "ravager";
						break;
				}

				if (fullType == null)
					return;

				String[] split = fullType.split(":");
				String type = split[0];
				boolean isBaby = split.length > 1 && split[1].equals("baby");

				if (!Config.MOB_CONFIG.getConfig().getBoolean(type + ".enabled"))
					return;

				if (killer.hasPermission("morph.bypasskill." + type))
					return;
				if (!killer.hasPermission("morph.into." + type))
					return;
				if (stringList.contains(type))
					return;

				int killsRequired = Config.MOB_CONFIG.getConfig().getInt(type + ".requiredKills");
				int currKills = 0;
				if (fileConfig.get("progress." + type) != null) {
					currKills = fileConfig.getInt("progress." + type);
				}
				currKills++;

				if (currKills < killsRequired) {
					String msg = msgs.getMessage("morphProgress", currKills, killsRequired, type);
					if (msg != null) {
						killer.sendMessage(prefix + " " + msg);
					}
					fileConfig.set("progress." + type, currKills);

					try {
						fileConfig.save(userFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				stringList.add(type);
				fileConfig.set("Mobs", stringList);
				try {
					fileConfig.save(userFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				String msgType = isBaby ? "baby " + type : type;
				//Just incase the key doesn't exist, I forget it in earlier versions
				String msg = msgs.getMessage("youCanNowMorph", msgType, "", "");
				if (msg != null) {
					killer.sendMessage(prefix + " " + msg);
				}

				if (pl.getConfig().getBoolean("morphOnKill")) {
					morph.morphPlayer(killer, DisguiseType.valueOf(type.toUpperCase()), false, isBaby);
				}
			}
		}
	}
}