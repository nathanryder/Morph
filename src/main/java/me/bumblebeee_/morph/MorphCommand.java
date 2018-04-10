package me.bumblebeee_.morph;

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

public class MorphCommand implements CommandExecutor {

    Messages m = new Messages();
    Inventorys inv = new Inventorys();
    String prefix = m.getMessage("prefix");
    MorphManager morph = new MorphManager();
    Plugin pl = Morph.pl;


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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
                DisguiseType type = getDisguiseType(args[1]);
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

                if (baby && !morph.isAllowBaby(type.toReadable())) {
                    sender.sendMessage(prefix + " " + m.getMessage("noBabyType"));
                    return false;
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

            DisguiseType type = getDisguiseType(args[1]);

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

            if (baby && !morph.isAllowBaby(type.toReadable())) {
                sender.sendMessage(prefix + " " + m.getMessage("noBabyType"));
                return false;
            }

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
        } else if (cmd.getName().equalsIgnoreCase("morph")) {
            if (!(sender instanceof Player)) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (sender.hasPermission("morph.reload")) {
                            pl.reloadConfig();
                            Morph.health = !pl.getConfig().getBoolean("disableHealthSystem");
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
                        Morph.health = !pl.getConfig().getBoolean("disableHealthSystem");
                        p.sendMessage(prefix + " " + m.getMessage("reloadedConfig"));
                    }
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
                    args[0] = "pig_zombie:baby";
                } else if (args[0].equalsIgnoreCase("zombiepig:baby")) {
                    args[0] = "pig_zombie:baby";
                } else if (args[0].equalsIgnoreCase("zombiepigman:baby")) {
                    args[0] = "pig_zombie:baby";
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
                    args[0] = "pig_zombie";
                } else if (args[0].equalsIgnoreCase("zombiepig")) {
                    args[0] = "pig_zombie";
                } else if (args[0].equalsIgnoreCase("zombiepigman")) {
                    args[0] = "pig_zombie";
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
                }
            }

            DisguiseType type = getDisguiseType(args[0]);

            if (type == null) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("near")) {
                        int radius = Morph.pl.getConfig().getInt("near-radius");
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
                    } else if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.DARK_PURPLE + "-----------------------------------------");
                        for (String cmmd : m.getCommands()) {
                            p.sendMessage(prefix + " /" + m.getMessage("helpFormat", cmmd, m.getMessage("commands." + cmmd)));
                        }
                        p.sendMessage(ChatColor.GREEN + "[Morph] " + ChatColor.DARK_PURPLE + "-----------------------------------------");
                        return true;
                    } else if (args[0].equalsIgnoreCase("toggle")) {
                        if (p.hasPermission("morph.toggle")) {
                            morph.toggleAbilty(p);
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("view")) {
                        boolean playerChangeView = Morph.pl.getConfig().getBoolean("canChangeView");

                        if (!playerChangeView) {
                            p.sendMessage(m.getMessage("unableToChangeView"));
                            return false;
                        }

                        if (!p.hasPermission("morph.changeview")) {
                            p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                            return true;
                        }

                        if (!(args.length > 1)) {
                            boolean ownView = fileConfig.getBoolean("viewDisguise");
                            if (fileConfig.getString("viewDisguise") == null)
                                ownView = Morph.pl.getConfig().getBoolean("viewSelfDisguise");

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
                }

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        String useMobs = stringList.toString().replace("[", "").replace("]" ,"");
                        if (useMobs.length() == 0) {
                            send(p, prefix + " " + m.getMessage("cannotMorphAsAnything"));
                            return true;
                        }
                        p.sendMessage(prefix + " " + m.getMessage("canMorphInto", "", p.getDisplayName(), "", useMobs));
                        return true;
                    }
                }

                if (args[0].equalsIgnoreCase("status")) {
                    if (DisguiseAPI.isDisguised(p)) {
                        p.sendMessage(prefix + " " + m.getMessage("morphedAs", "", p.getDisplayName(), DisguiseAPI.getDisguise(p).getType().name().toLowerCase(), ""));
                        return true;
                    } else {
                        p.sendMessage(prefix + " " + m.getMessage("notMorphedAsAnything"));
                        return true;
                    }

                }

                p.sendMessage(prefix + " " + m.getMessage("invalidMorph"));
                return true;
            }

            if (args[0].equalsIgnoreCase("player")) {
                if (pl.getConfig().getString("enable-players") == "true") {
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


                        if (!players.contains(off.getName())) {
                            send(p, prefix + " " + m.getMessage("unableToMorphAsPlayer", off.getName(), p.getDisplayName(), "", ""));
                            return true;
                        }

                        PlayerDisguise d = new PlayerDisguise(off.getName());
                        DisguiseAPI.disguiseToAll(p, d);
                        send(p, prefix + " " + m.getMessage("morphedAsPlayer", off.getName(), p.getDisplayName(), "", ""));

                        return true;

                    } else if (t != null) {
                        on = Bukkit.getServer().getPlayer(args[1]);

                        if (!players.contains(on.getName())) {
                            send(p, prefix + " " + m.getMessage("UnableToMorphAsPlayer", on.getName(), p.getDisplayName(), "", ""));
                            return true;
                        }

                        PlayerDisguise d = new PlayerDisguise(on.getName());
                        DisguiseAPI.disguiseToAll(p, d);
                        send(p, prefix + " " + m.getMessage("morphedAsPlayer", on.getName(), p.getDisplayName(), "", ""));

                        return true;
                    }
                }
            }

            String perm = type.toReadable().toLowerCase().replace(" ", "_");

            if (!p.hasPermission("morph.into." + perm)) {
                if (!p.hasPermission("morph.bypasskill." + perm)) {
                    send(p, prefix + " " + m.getMessage("noPermissions"));
                    return true;
                }
            }

            boolean baby = false;
            if (args.length > 1) {
                baby = args[1].equalsIgnoreCase("baby");
            }

            if (Morph.using.containsKey(p.getUniqueId())) {
                String using = morph.getUsing(p);
                if (baby) {
                    if (using.equalsIgnoreCase(type.toReadable().toLowerCase())) {
                        if (morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), "baby " + type.toReadable(), ""));
                            return true;
                        }
                    }
                } else {
                    if (using.equalsIgnoreCase(type.toReadable().toLowerCase())) {
                        if (!morph.isBaby(p)) {
                            send(p, prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), type.toReadable(), ""));

                            return true;
                        }
                    }
                }
            }

            if (baby && !morph.isAllowBaby(type.toReadable())) {
                send(p, prefix + " " + m.getMessage("noBabyType"));
                return false;
            }

            if (!p.hasPermission("morph.bypasskill." + perm)) {
                if (baby) {
                    if (!stringList.contains(type.toString().toLowerCase() + ":baby")) {
                        p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), "baby " + type.toReadable(), ""));
                        return true;
                    }
                } else {
                    if (!stringList.contains(type.toString().toLowerCase())) {
                        p.sendMessage(prefix + " " + m.getMessage("unableToMorph", "", p.getDisplayName(), type.toReadable(), ""));
                        return true;
                    }
                }
            }

//            Morph.undisguiseBuffer.add(p.getUniqueId());
            DisguiseAPI.undisguiseToAll(p);
            Morph.using.remove(p.getUniqueId());

            morph.morphPlayer(p, type, false, baby);
        } else if (cmd.getName().equalsIgnoreCase("unmorph")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + " " + "You cannot use this command!");
                    return true;
                }
            }

            final Player p = (Player) sender;

            if (args.length == 1) {
                Player t = Bukkit.getServer().getPlayer(args[0]);

                if (args[0].equalsIgnoreCase("all")) {
                    if (!p.hasPermission("morph.morph.modify")) {
                        p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return true;
                    }
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {

                        if (!DisguiseAPI.isDisguised(pl)) {
                            p.sendMessage(prefix + " " + m.getMessage("noPlayersMorphed"));
                            return true;
                        }
                        morph.unmorphPlayer(p, true, false);
                    }
                    sender.sendMessage(prefix + " " + m.getMessage("unmorphedAllPlayers", t.getDisplayName(), p.getDisplayName(), "", ""));
                    return true;
                }
            }
            morph.unmorphPlayer(p, false, false);
            return true;

        }
        return true;
    }

    private DisguiseType getDisguiseType(String arg) {
        for (DisguiseType type : DisguiseType.values()) {
            String t = type.toString().toUpperCase();
            if (arg.toUpperCase().equals(t)) return type;
        }
        return null;
    }

    public static void send(Player p, String s) {
        p.sendMessage(s);
    }
}
