package me.bumblebeee_.morph;

import me.bumblebeee_.morph.morphs.Morph;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class MorphCommand implements CommandExecutor {

    Messages m = new Messages();
    Inventorys inv = new Inventorys();
    Plugin pl = Main.pl;


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        MorphManager morph = Main.getMorphManager();
        String prefix = m.getMessage("prefix");
        if (cmd.getName().equalsIgnoreCase("delmorph")) {
            if (!sender.hasPermission("morph.morph.modify")) {
                sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidArguments"));
                return true;
            }

            if (args[1].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")) {
                Player t = Bukkit.getServer().getPlayer(args[0]);

                if (t == null) {
                    sender.sendMessage(prefix + " " + m.getMessage("invalidPlayer").replace("{target}", args[0]));
                    return true;
                }

                File userFile = new File(pl.getDataFolder() + "/UserData/" + t.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                fileConfig.set("Mobs", null);
                try {
                    fileConfig.save(userFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                sender.sendMessage(prefix + " " + m.getMessage("removedMorph", t.getDisplayName(), sender.getName(), "all", ""));
                return true;
            } else {
                Morph type = Main.getMorphManager().getMorphType(args[1].toLowerCase());
                Player t = Bukkit.getServer().getPlayer(args[0]);

                if (t == null) {
                    sender.sendMessage(prefix + " " + m.getMessage("invalidPlayer").replace("{target}", args[0]));
                    return true;
                }

                if (type == null) {
                    sender.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                    return true;
                }
                File userFile = new File(pl.getDataFolder() + "/UserData/" + t.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                List<String> stringList = fileConfig.getStringList("Mobs");

                boolean baby = false;
                if (args.length > 2) {
                    baby = args[2].equalsIgnoreCase("baby");
                }

                if (baby) {
                    stringList.remove(args[1] + ":baby");
                    sender.sendMessage(prefix + " " + m.getMessage("removedMorph", t.getDisplayName(), sender.getName(), "baby " + args[1].toLowerCase(), ""));
                } else {
                    stringList.remove(args[1]);
                    sender.sendMessage(prefix + " " + m.getMessage("removedMorph", t.getDisplayName(), sender.getName(), args[1].toLowerCase(), ""));
                }
                fileConfig.set("Mobs", stringList);
                try {
                    fileConfig.save(userFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("addmorph")) {
            if (!sender.hasPermission("morph.morph.modify")) {
                sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidArguments"));
                return true;
            }

            Morph type = Main.getMorphManager().getMorphType(args[1].toLowerCase());

            if (type == null) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                return true;
            }

            Player t = Bukkit.getServer().getPlayer(args[0]);
            if (t == null) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidPlayer").replace("{target}", args[0]));
                return true;
            }

            boolean baby = false;
            if (args.length > 2) {
                baby = args[2].equalsIgnoreCase("baby");
            }

            File userFile = new File(pl.getDataFolder() + "/UserData/" + t.getUniqueId() + ".yml");
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
            List<String> stringList = fileConfig.getStringList("Mobs");

            if (baby) {
                if (stringList.contains(args[1] + ":baby")) {
                    sender.sendMessage(prefix + " " + m.getMessage("targetCanAlreadyMorph", t.getDisplayName(), sender.getName(), args[1].toLowerCase(), ""));
                    return true;
                }
                stringList.add(args[1] + ":baby");
                sender.sendMessage(prefix + " " + m.getMessage("addedMorphToTarget", t.getDisplayName(), sender.getName(), "baby " + args[1].toLowerCase(), ""));
            } else {
                if (stringList.contains(args[1])) {
                    sender.sendMessage(prefix + " " + m.getMessage("targetCanAlreadyMorph", t.getDisplayName(), sender.getName(), args[1].toLowerCase(), ""));
                    return true;
                }
                stringList.add(args[1]);
                sender.sendMessage(prefix + " " + m.getMessage("addedMorphToTarget", t.getDisplayName(), sender.getName(), args[1].toLowerCase(), ""));
            }

            fileConfig.set("Mobs", stringList);
            try {
                fileConfig.save(userFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("forcemorph")) {
            if (!sender.hasPermission("morph.forcemorph")) {
                sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                return true;
            }
            
            if (args.length < 2) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidArguments"));
                return true;
            }

            Player p = Bukkit.getServer().getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidPlayer").replace("{target}", args[0]));
                return true;
            }

            boolean ignorePerms = false;
            boolean silent = false;
            if (args.length > 2) {
                ignorePerms = args[2].equalsIgnoreCase("true");

                if (args.length > 3) {
                    silent = args[3].equalsIgnoreCase("true");
                }
            }

            boolean baby = args[1].split(":").length > 1;
            if (args[1].contains("irongolem")) {
                args[1] = "iron_golem";
            } else if (args[1].contains("polar")) {
                args[1] = "polar_bear";
            } else if (args[1].contains("polarbear")) {
                args[1] = "polar_bear";
            } else if (args[1].contains("bear")) {
                args[1] = "polar_bear";
            } else if (args[1].contains("pigzombie")) {
                args[1] = "zombified_piglin";
            } else if (args[1].contains("zombiepig")) {
                args[1] = "zombified_piglin";
            } else if (args[1].contains("zombiepigman")) {
                args[1] = "zombified_piglin";
            } else if (args[1].contains("dragon")) {
                args[1] = "ender_dragon";
            } else if (args[1].contains("enderdragon")) {
                args[1] = "ender_dragon";
            } else if (args[1].contains("mushroom")) {
                args[1] = "mushroom_cow";
            } else if (args[1].contains("mushroomcow")) {
                args[1] = "mushroom_cow";
            } else if (args[1].contains("zombievillager")) {
                args[1] = "zombie_villager";
            } else if (args[1].contains("piglinbrute")) {
                args[1] = "piglin_brute";
            }

            Morph type = Main.getMorphManager().getMorphType(args[1].toLowerCase());

            if (type == null) {
                sender.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                return true;
            }

            String morphName = type.getMorphName();
            if (Main.using.containsKey(p.getUniqueId())) {
                String using = morph.getUsing(p);
                if (baby) {
                    if (using.equalsIgnoreCase(morphName)) {
                        if (morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                            return true;
                        }
                    }
                } else {
                    if (using.equalsIgnoreCase(morphName)) {
                        if (!morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), type.toFriendly(), ""));

                            return true;
                        }
                    }
                }
            }

            if (!ignorePerms) {
                if (!p.hasPermission("morph.into." + morphName)) {
                    if (!p.hasPermission("morph.bypasskill." + morphName)) {
                        send(p, prefix + " " + m.getMessage("noPermissions"));
                        sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return true;
                    }
                }

                File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                List<String> stringList = fileConfig.getStringList("Mobs");
                if (!p.hasPermission("morph.bypasskill." + morphName)) {
                    if (baby) {
                        if (!stringList.contains(type.toString().toLowerCase() + ":baby")) {
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                            sender.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                            return true;
                        }
                    } else {
                        if (!stringList.contains(type.toString().toLowerCase())) {
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), type.toFriendly(), ""));
                            sender.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), type.toFriendly(), ""));
                            return true;
                        }
                    }
                }
            }

            DisguiseAPI.undisguiseToAll(p);
            Main.using.remove(p.getUniqueId());

            morph.morphPlayer(p, type, silent, baby);
            sender.sendMessage(prefix + " " + "Successfully force morphed player!");
        } else if (cmd.getName().equalsIgnoreCase("morph")) {
            if (!(sender instanceof Player)) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (sender.hasPermission("morph.reload")) {
                            pl.reloadConfig();
                            for (Config config : Config.values()) {
                                config.createOrLoad();
                            }
                            m.setup();
                            Main.health = !pl.getConfig().getBoolean("disableHealthSystem");
                            sender.sendMessage(prefix + " " + m.getMessage("reloadedConfig"));
                            return true;
                        }
                    } else {
                        sender.sendMessage(prefix + " " + "You cannot use this command!");
                        return true;
                    }
                } else {
                    sender.sendMessage(prefix + " " + "You cannot use this command!");
                    return true;
                }
            }

            final Player p = (Player) sender;
            if (!p.hasPermission("morph.morph")) {
                p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                return true;
            }

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (p.hasPermission("morph.reload")) {
                        pl.reloadConfig();
                        Main.health = !pl.getConfig().getBoolean("disableHealthSystem");
                        p.sendMessage(prefix + " " + m.getMessage("reloadedConfig"));
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("giveitem")) {
                    if (!sender.hasPermission("morph.giveitem")) {
                        sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return false;
                    }

                    p.getInventory().addItem(morph.getMorphItem());
                    return true;
                }
            }

            if (!(pl.getConfig().getStringList("enabled-worlds").contains(p.getWorld().getName()))) {
                if (pl.getConfig().getBoolean("debug")) {
                    pl.getLogger().info("Worlds: " + pl.getConfig().getStringList("enabled-worlds"));
                }
                if (!(pl.getConfig().getStringList("enabled-worlds").contains("<all>"))) {
                    if (pl.getConfig().getBoolean("debug")) {
                        pl.getLogger().info("Did not find <all>... Disabled.");
                    }
                    send(p, prefix + " " + m.getMessage("disableInThisWorld"));
                    return true;
                }
            }

            File userFile = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
            List<String> stringList = fileConfig.getStringList("Mobs");
            List<String> players = fileConfig.getStringList("Players");

            if (args.length == 0) {
                inv.openMorph(p, 1);
                return true;
            }

            if (args[0].split(" ").length > 1) {
                if (args[0].equalsIgnoreCase("irongolem:baby")) {
                    args[0] = "iron_golem:baby";
                } else if (args[0].equalsIgnoreCase("polar:baby")) {
                    args[0] = "polar_bear:baby";
                } else if (args[0].equalsIgnoreCase("polarbear:baby")) {
                    args[0] = "polar_bear:baby";
                } else if (args[0].equalsIgnoreCase("bear:baby")) {
                    args[0] = "polar_bear:baby";
                } else if (args[0].equalsIgnoreCase("pigzombie:baby")) {
                    args[0] = "zombified_piglin:baby";
                } else if (args[0].equalsIgnoreCase("zombiepig:baby")) {
                    args[0] = "zombified_piglin:baby";
                } else if (args[0].equalsIgnoreCase("zombiepigman:baby")) {
                    args[0] = "zombified_piglin:baby";
                } else if (args[0].equalsIgnoreCase("dragon:baby")) {
                    args[0] = "ender_dragon:baby";
                } else if (args[0].equalsIgnoreCase("enderdragon:baby")) {
                    args[0] = "ender_dragon:baby";
                } else if (args[0].equalsIgnoreCase("mushroom:baby")) {
                    args[0] = "mushroom_cow:baby";
                } else if (args[0].equalsIgnoreCase("mushroomcow:baby")) {
                    args[0] = "mushroom_cow:baby";
                } else if (args[0].equalsIgnoreCase("zombievillager:baby")) {
                    args[0] = "zombie_villager:baby";
                } else if (args[0].equalsIgnoreCase("piglinbrute")) {
                    args[0] = "piglin_brute";
                } else if (args[0].equalsIgnoreCase("glowsquid")) {
                    args[0] = "glow_squid";
                }
            } else {
                if (args[0].equalsIgnoreCase("irongolem")) {
                    args[0] = "iron_golem";
                } else if (args[0].equalsIgnoreCase("polar")) {
                    args[0] = "polar_bear";
                } else if (args[0].equalsIgnoreCase("polarbear")) {
                    args[0] = "polar_bear";
                } else if (args[0].equalsIgnoreCase("bear")) {
                    args[0] = "polar_bear";
                } else if (args[0].equalsIgnoreCase("pigzombie")) {
                    args[0] = "zombified_piglin";
                } else if (args[0].equalsIgnoreCase("zombiepig")) {
                    args[0] = "zombified_piglin";
                } else if (args[0].equalsIgnoreCase("zombiepigman")) {
                    args[0] = "zombified_piglin";
                } else if (args[0].equalsIgnoreCase("dragon")) {
                    args[0] = "ender_dragon";
                } else if (args[0].equalsIgnoreCase("enderdragon")) {
                    args[0] = "ender_dragon";
                } else if (args[0].equalsIgnoreCase("mushroom")) {
                    args[0] = "mushroom_cow";
                } else if (args[0].equalsIgnoreCase("mushroomcow")) {
                    args[0] = "mushroom_cow";
                } else if (args[0].equalsIgnoreCase("zombievillager")) {
                        args[0] = "zombie_villager";
                } else if (args[0].equalsIgnoreCase("piglinbrute")) {
                    args[0] = "piglin_brute";
                } else if (args[0].equalsIgnoreCase("glowsquid")) {
                    args[0] = "glow_squid";
                }
            }

            Morph type = Main.getMorphManager().getMorphType(args[0].toLowerCase());
            if (type == null) {
                if (args[0].equalsIgnoreCase("near")) {
                    int radius = Main.pl.getConfig().getInt("near-radius");
                    List<Entity> near = p.getNearbyEntities(radius, radius, radius);
                    if (near.toString().contains("CraftPlayer")) {
                        for (Entity e : near) {
                            if (e instanceof Player) {
                                if (DisguiseAPI.isDisguised(e)) {
                                    p.sendMessage(prefix + " " + m.getMessage("morphedNearby", e.getName(), p.getDisplayName(), DisguiseAPI.getDisguise(e).getType().toString().toLowerCase(), ""));
                                    return true;
                                }
                            }
                        }
                    } else {
                        p.sendMessage(prefix + " " + m.getMessage("nobodyNearby", "", p.getDisplayName(), "", ""));
                        return true;
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(prefix + " " + ChatColor.DARK_PURPLE + "-----------------------------------------");
                    for (String cmmd : m.getCommands()) {
                        p.sendMessage(prefix + " /" + m.getMessage("helpFormat", cmmd, m.getMessage("commands." + cmmd)));
                    }
                    p.sendMessage(prefix + " " + ChatColor.DARK_PURPLE + "-----------------------------------------");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("info")) {
                    if (args.length < 2) {
                        sender.sendMessage(prefix + " " + m.getMessage("invalidArguments"));
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("irongolem")) {
                        args[1] = "iron_golem";
                    } else if (args[1].equalsIgnoreCase("polar")) {
                        args[1] = "polar_bear";
                    } else if (args[1].equalsIgnoreCase("polarbear")) {
                        args[1] = "polar_bear";
                    } else if (args[1].equalsIgnoreCase("bear")) {
                        args[1] = "polar_bear";
                    } else if (args[1].equalsIgnoreCase("pigzombie")) {
                        args[1] = "zombified_piglin";
                    } else if (args[1].equalsIgnoreCase("zombiepig")) {
                        args[1] = "zombified_piglin";
                    } else if (args[1].equalsIgnoreCase("zombiepigman")) {
                        args[1] = "zombified_piglin";
                    } else if (args[1].equalsIgnoreCase("dragon")) {
                        args[1] = "ender_dragon";
                    } else if (args[1].equalsIgnoreCase("enderdragon")) {
                        args[1] = "ender_dragon";
                    } else if (args[1].equalsIgnoreCase("mushroom")) {
                        args[1] = "mushroom_cow";
                    } else if (args[1].equalsIgnoreCase("mushroomcow")) {
                        args[1] = "mushroom_cow";
                    } else if (args[1].equalsIgnoreCase("zombievillager")) {
                        args[1] = "zombie_villager";
                    } else if (args[1].equalsIgnoreCase("piglinbrute")) {
                        args[0] = "piglin_brute";
                    }

                    Morph check = Main.getMorphManager().getMorphType(args[1].toLowerCase());
                    if (check == null) {
                        p.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                        return true;
                    }

                    p.sendMessage(prefix + " " + m.getMessage("abilityInfo.title", args[1].toLowerCase(), "", ""));

                    List<String> info = m.getListMessage("abilityInfo." + args[1].toLowerCase());
                    for (String line : info) {
                        p.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', line));
                    }

                    return true;
                }
                else if (args[0].equalsIgnoreCase("toggle")) {
                    if (p.hasPermission("morph.toggle")) {
                        morph.toggleAbilty(p);
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("view")) {
                    boolean playerChangeView = Main.pl.getConfig().getBoolean("canChangeView");

                    if (!playerChangeView) {
                        p.sendMessage(prefix + " " + m.getMessage("unableToChangeView"));
                        return false;
                    }

                    if (!p.hasPermission("morph.changeview")) {
                        p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return true;
                    }

                    if (!(args.length > 1)) {
                        boolean ownView = fileConfig.getBoolean("viewDisguise");
                        if (fileConfig.getString("viewDisguise") == null)
                            ownView = Main.pl.getConfig().getBoolean("viewSelfDisguise");

                        morph.setViewMorph(p, !ownView);
                        return true;
                    } else {
                        boolean view;
                        try {
                            view = Boolean.parseBoolean(args[1]);
                        } catch (Exception e) {
                            sender.sendMessage(prefix + " " + m.getMessage("invalidArguments"));
                            return false;
                        }

                        morph.setViewMorph(p, view);
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("status")) {
                    if (DisguiseAPI.isDisguised(p)) {
                        p.sendMessage(prefix + " " + m.getMessage("morphedAs", "", p.getDisplayName(), DisguiseAPI.getDisguise(p).getType().name().toLowerCase(), ""));
                        return true;
                    } else {
                        p.sendMessage(prefix + " " + m.getMessage("notMorphedAsAnything"));
                        return true;
                    }

                }
                else if (args[0].equalsIgnoreCase("player")) {
                    if (pl.getConfig().getBoolean("enable-players")) {
                        if (!(args.length == 2)) {
                            send(p, prefix + " " + m.getMessage("invalidArguments"));
                            return true;
                        }

                        Player t = Bukkit.getServer().getPlayer(args[1]);
                        Player on;
                        OfflinePlayer off;

                        if (t == null) {
                            off = Bukkit.getServer().getOfflinePlayer(args[1]);

                            if (!(off.hasPlayedBefore())) {
                                send(p, prefix + " " + m.getMessage("couldNotFindPlayer", "", p.getDisplayName(), "", ""));
                                return true;
                            }

                            if (pl.getConfig().getStringList("blacklisted-players").contains(off.getName())) {
                                return true;
                            }

                            if (!players.contains(off.getName()) && !p.hasPermission("morph.into.player." + off.getName())) {
                                send(p, prefix + " " + m.getMessage("unableToMorphAsPlayer", off.getName(), p.getDisplayName(), "", ""));
                                return true;
                            }

                            PlayerDisguise d = new PlayerDisguise(off.getName());
                            DisguiseAPI.disguiseToAll(p, d);
                            send(p, prefix + " " + m.getMessage("morphedAsPlayer", off.getName(), p.getDisplayName(), "", ""));

                            return true;

                        } else {
                            on = Bukkit.getServer().getPlayer(args[1]);

                            if (pl.getConfig().getStringList("blacklisted-players").contains(on.getName())) {
                                return true;
                            }

                            if (!players.contains(on.getName()) && !p.hasPermission("morph.into.player." + on.getName())) {
                                send(p, prefix + " " + m.getMessage("unableToMorphAsPlayer", on.getName(), p.getDisplayName(), "", ""));
                                return true;
                            }

                            PlayerDisguise d = new PlayerDisguise(on.getName());
                            DisguiseAPI.disguiseToAll(p, d);
                            send(p, prefix + " " + m.getMessage("morphedAsPlayer", on.getName(), p.getDisplayName(), "", ""));

                            return true;
                        }
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
//                        String useMobs = stringList.toString().replace("[", "").replace("]" ,"");
                        StringBuilder useMobs = new StringBuilder();
                        for (String mob : stringList) {
                            if (mob.contains(":baby")) {
                                mob = "baby " + mob.split(":")[0];
                            }

                            if (useMobs.length() == 0) {
                                useMobs.append(mob);
                            } else {
                                useMobs.append(", ").append(mob);
                            }
                        }

                        if (useMobs.length() == 0) {
                            send(p, prefix + " " + m.getMessage("cannotMorphAsAnything"));
                            return true;
                        }

                        p.sendMessage(prefix + " " + m.getMessage("canMorphInto", "", p.getDisplayName(), "", useMobs.toString()));
                        return true;
                    }
                }

                p.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                return true;
            }


            if (!p.hasPermission("morph.into." + type.getMorphName())) {
                if (!p.hasPermission("morph.bypasskill." + type.getMorphName())) {
                    send(p, prefix + " " + m.getMessage("noPermissions"));
                    return true;
                }
            }

            boolean baby = false;
            if (args.length > 1) {
                baby = args[1].equalsIgnoreCase("baby");
            }

            if (Main.using.containsKey(p.getUniqueId())) {
                String using = morph.getUsing(p);
                if (baby) {
                    if (using.equalsIgnoreCase(type.getMorphName())) {
                        if (morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                            return true;
                        }
                    }
                } else {
                    if (using.equalsIgnoreCase(type.getMorphName())) {
                        if (!morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), type.toFriendly(), ""));

                            return true;
                        }
                    }
                }
            }

            if (!p.hasPermission("morph.bypasskill." + type.getMorphName())) {
                if (baby) {
                    if (!stringList.contains(type.getMorphName() + ":baby")) {
                        p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                        return true;
                    }
                } else {
                    if (!stringList.contains(type.getMorphName())) {
                        p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), type.toFriendly(), ""));
                        return true;
                    }
                }
            }

//            Morph.undisguiseBuffer.add(p.getUniqueId());
            DisguiseAPI.undisguiseToAll(p);
            Main.using.remove(p.getUniqueId());

            morph.morphPlayer(p, type, false, baby);
        } else if (cmd.getName().equalsIgnoreCase("unmorph")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + " " + "You cannot use this command!");
                    return true;
                }
            }


            if (args.length >= 1) {
                if (!sender.hasPermission("morph.morph.modify")) {
                    sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                    return true;
                }
                
                if (args[0].equalsIgnoreCase("all")) {
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        morph.unmorphPlayer(pl, true, false);
                    }
                    sender.sendMessage(prefix + " " + m.getMessage("unmorphedAllPlayers"));

                    return true;
                } else {
                    Player t = Bukkit.getServer().getPlayer(args[0]);
                    if (t == null) {
                        sender.sendMessage(prefix + " " + m.getMessage("invalidPlayer").replace("{target}", args[0]));
                        return true;
                    }

                    boolean staff = true;
                    if (args.length > 1) {
                        staff = args[1].equalsIgnoreCase("true");
                    }

                    morph.unmorphPlayer(t, staff, false);
                    return true;
                }

            }

            Player p = (Player) sender;
            morph.unmorphPlayer(p, false, false);
            return true;

        } else if (cmd.getName().equalsIgnoreCase("randommorph")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + " " + "You cannot use this command!");
                return true;
            }
            Player p = (Player) sender;

            if (!sender.hasPermission("morph.randommorph")) {
                sender.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                return true;
            }

            List<String> mobs = pl.getConfig().getStringList("randomMorph");
            NavigableMap<Integer, Morph> weighedMap = new TreeMap<>();
            int totalWeight = 0;
            for (String mob : mobs) {
                Morph type = Main.getMorphManager().getMorphs().get(mob);
                if (type == null) {
                    Bukkit.getServer().getLogger().warning("Failed to find a morph named " + mob + " while trying to pick a random morph");
                    continue;
                }

                totalWeight += pl.getConfig().getInt("randomMorph." + mob);
                weighedMap.put(totalWeight, type);
            }

            if (totalWeight == 0) {
                weighedMap = getDefaultRandomMorphs();
                totalWeight = weighedMap.lastEntry().getKey();
            }

            Random rand = new Random();
            int pick = rand.nextInt(totalWeight);
            Morph choice = weighedMap.higherEntry(pick).getValue();

            Main.getMorphManager().morphPlayer(p, choice, false, false);
            return true;
        }
        return true;
    }

    public NavigableMap<Integer, Morph> getDefaultRandomMorphs() {
        NavigableMap<Integer, Morph> weighedMap = new TreeMap<>();
        int weight = 0;

        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("horse"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("skeleton_horse"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("wolf"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("ocelot"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("cow"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("pig"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("wither_skeleton"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("bat"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("blaze"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("cave_spider"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("chicken"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("creeper"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("enderman"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("endermite"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("ghast"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("guardian"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("iron_golem"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("magma_cube"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("mushroom_cow"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("zombified_piglin"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("sheep"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("silverfish"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("skeleton"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("slime"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("snowman"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("spider"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("squid"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("villager"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("witch"));
        weighedMap.put(weight += 1, Main.getMorphManager().getMorphType("wither"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("llama"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("vex"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("vindicator"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("evoker"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("zombie"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("rabbit"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("giant"));
        weighedMap.put(weight += 1, Main.getMorphManager().getMorphType("ender_dragon"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("mule"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("donkey"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("zombie_villager"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("parrot"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("illusioner"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("stray"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("husk"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("dolphin"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("drowned"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("cod"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("salmon"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("tropical_fish"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("pufferfish"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("phantom"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("turtle"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("bee"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("strider"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("hoglin"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("zoglin"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("shulker"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("polar_bear"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("cat"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("fox"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("panda"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("pillager"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("piglinbrute"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("piglin"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("ravager"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("goat"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("glow_squid"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("axolotl"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("allay"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("frog"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("tadpole"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("warden"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("camel"));
        weighedMap.put(weight += 20, Main.getMorphManager().getMorphType("sniffer"));

        return weighedMap;
    }

    public static void send(Player p, String s) {
        p.sendMessage(s);
    }
}
