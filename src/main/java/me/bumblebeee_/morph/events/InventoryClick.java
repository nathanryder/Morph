package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Inventorys;
import me.bumblebeee_.morph.Messages;
import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class InventoryClick implements Listener {

    Inventorys inv = new Inventorys();
    Messages m = new Messages();
    MorphManager morph = new MorphManager();
    String prefix = m.getMessage("prefix");

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        String morphedTitle = m.getMessage("morphedTitle").replace("{mob}", "");
        String unmorphedTitle = m.getMessage("unmorphedTitle");
        if (e.getInventory().getName().startsWith(morphedTitle) ||
                e.getInventory().getName().equalsIgnoreCase(unmorphedTitle)) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

            Player p = (Player) e.getWhoClicked();
            ItemStack i = e.getCurrentItem();
            String dis = ChatColor.stripColor(i.getItemMeta().getDisplayName());

            if (dis.equalsIgnoreCase("Close")) {
                p.closeInventory();
            } else if (dis.equalsIgnoreCase("Previous")) {
                if (Inventorys.pages.containsKey(p.getUniqueId())) {
                    int page = Inventorys.pages.get(p.getUniqueId());
                    Inventorys.pages.remove(p.getUniqueId());
                    if (page == 1) {
                        Inventorys.pages.put(p.getUniqueId(), 2);
                        inv.openMorph(p, 3);
                    } else if (page == 3) {
                        Inventorys.pages.put(p.getUniqueId(), 2);
                        inv.openMorph(p, 2);
                    } else {
                        Inventorys.pages.put(p.getUniqueId(), 1);
                        inv.openMorph(p, 1);
                    }
                } else {
                    Inventorys.pages.put(p.getUniqueId(), 3);
                    inv.openMorph(p, 3);
                }
            } else if (dis.equalsIgnoreCase("Next")) {
                if (Inventorys.pages.containsKey(p.getUniqueId())) {
                    int page = Inventorys.pages.get(p.getUniqueId());
                    if (page == 2) {
                        Inventorys.pages.remove(p.getUniqueId());
                        Inventorys.pages.put(p.getUniqueId(), 3);
                        inv.openMorph(p, 3);
                    } else if (page == 3) {
                        Inventorys.pages.remove(p.getUniqueId());
                        Inventorys.pages.put(p.getUniqueId(), 1);
                        inv.openMorph(p, 1);
                    } else {
                        Inventorys.pages.remove(p.getUniqueId());
                        Inventorys.pages.put(p.getUniqueId(), page + 1);
                        inv.openMorph(p, page + 1);
                    }
                } else {
                    Inventorys.pages.put(p.getUniqueId(), 2);
                    inv.openMorph(p, 2);
                }
            } else if (dis.equalsIgnoreCase("Settings")) {
                inv.openOptions(p);
            } else if (dis.equalsIgnoreCase("Click to unmorph")) {
                p.closeInventory();
                morph.unmorphPlayer(p, false, false);
            } else if (dis.contains("Click to morph into")) {
                p.closeInventory();
                File userFile = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                List<String> stringList = fileConfig.getStringList("Mobs");
                List<String> players = fileConfig.getStringList("Players");

                Inventorys.pages.remove(p.getUniqueId());
                if (Inventorys.pages.containsKey(p.getUniqueId())) {
                    //Is trying to morph as player
                    if (Morph.pl.getConfig().getBoolean("enable-players")) {
                        String[] split = dis.split(" ");
                        String name = split[4];

                        Player t = Bukkit.getServer().getPlayer(name);
                        Player on;
                        OfflinePlayer off;

                        if (t == null) {
                            off = Bukkit.getServer().getOfflinePlayer(name);

                            if (!players.contains(off.getName())) {
                                p.sendMessage(prefix + " " + m.getMessage("unableToMorphAsPlayer", off.getName(), p.getDisplayName(), "", ""));
                                return;
                            }

                            PlayerDisguise d = new PlayerDisguise(off.getName());
                            DisguiseAPI.disguiseToAll(p, d);
                            p.sendMessage(prefix + " " + m.getMessage("morphedAsPlayer", off.getName(), p.getDisplayName(), "", ""));

                            return;

                        } else {
                            on = Bukkit.getServer().getPlayer(name);

                            if (!players.contains(on.getName())) {
                                p.sendMessage(prefix + " " + m.getMessage("UnableToMorphAsPlayer", on.getName(), p.getDisplayName(), "", ""));
                                return;
                            }

                            PlayerDisguise d = new PlayerDisguise(on.getName());
                            DisguiseAPI.disguiseToAll(p, d);
                            p.sendMessage(prefix + " " + m.getMessage("morphedAsPlayer", on.getName(), p.getDisplayName(), "", ""));
                            return;
                        }
                    }
                    return;
                }
                String[] split = dis.split(" ");
                String mobType;
                boolean baby = false;
                if (split.length > 6) {
                    mobType = split[5] + "_" + split[6];
                } else {
                    mobType = split[5];
                }

                if (mobType.split("_").length > 1) {
                    baby = mobType.split("_")[0].equalsIgnoreCase("baby");
                    if (baby) {
                        mobType = mobType.replace("baby_", "").replace("Baby_", "");
                    }
                }

                switch (mobType.toLowerCase()) {
                    case "polarbear":
                        mobType = "polar_bear";
                        break;
                    case "polar":
                        mobType = "polar_bear";
                        break;
                    case "cavespider":
                        mobType = "cave_spider";
                        break;
                    case "witherskeleton":
                        mobType = "wither_skeleton";
                        break;
                    case "mushroomcow":
                        mobType = "mushroom_cow";
                        break;
                    case "irongolem":
                        mobType = "iron_golem";
                        break;
                    case "magmacube":
                        mobType = "magma_cube";
                        break;
                    case "pigzombie":
                        mobType = "pig_zombie";
                        break;
                    case "enderdragon":
                        mobType = "ender_dragon";
                        break;
                    case "mushroom":
                        mobType = "mushroom_cow";
                        break;
                }

                DisguiseType type = getDisguiseType(mobType);
                if (type == null) {
                    p.sendMessage(ChatColor.RED + "An internal error has occurred. Please report this to an admin");
                    Morph.pl.getLogger().warning("Failed to find a mob named " + mobType);
                    return;
                }
                String perm = type.toReadable().toLowerCase().replace(" ", "_");

                if (type.toReadable().equalsIgnoreCase("enderman")) {
                    if (Morph.pl.getConfig().getBoolean("disable-endermen")) {
                        p.sendMessage(prefix + " " + m.getMessage("endermenDisabled"));
                        return;
                    }
                }

                if (!p.hasPermission("morph.into." + perm)) {
                    if (!p.hasPermission("morph.bypasskill." + perm)) {
                        p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return;
                    }
                }

                if (Morph.using.containsKey(p.getUniqueId())) {
                    String using = morph.getUsing(p);
                    if (using.equalsIgnoreCase(type.toReadable().toString().toLowerCase())) {
                        if (baby) {
                            if (morph.isBaby(p)) {
                                p.sendMessage(prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), "baby " + type.toReadable(), ""));
                                return;
                            }
                        } else {
                            if (!morph.isBaby(p)) {
                                p.sendMessage(prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), type.toReadable(), ""));
                                return;
                            }
                        }
                    }
                }

                if (!p.hasPermission("morph.bypasskill." + perm)) {
                    if (!stringList.contains(type.toString().toLowerCase())) {
                        if (baby)
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorphAsPlayer", "", p.getDisplayName(), "baby " + type.toReadable(), ""));
                        else
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorphAsPlayer", "", p.getDisplayName(), type.toReadable(), ""));

                        return;
                    }
                }
//                Morph.undisguiseBuffer.add(p.getUniqueId());
                DisguiseAPI.undisguiseToAll(p);
                Morph.using.remove(p.getUniqueId());

                morph.morphPlayer(p, type, false, baby);
            }
        } else if (e.getInventory().getName().equalsIgnoreCase("Morph Options")) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

            Player p = (Player) e.getWhoClicked();
            ItemStack i = e.getCurrentItem();
            String dis = ChatColor.stripColor(i.getItemMeta().getDisplayName());

            File f = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            boolean viewMorph = c.getBoolean("viewDisguise");

            if (dis.contains("View own morph")) {
                boolean playerChangeView = Morph.pl.getConfig().getBoolean("canChangeView");

                if (!p.hasPermission("morph.changeview")) {
                    p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                    return;
                }
                if (!playerChangeView) {
                    p.sendMessage(m.getMessage("unableToChangeView"));
                    return;
                }

                if (c.getString("viewDisguise") == null)
                    viewMorph = Morph.pl.getConfig().getBoolean("viewSelfDisguise");

                morph.setViewMorph(p, !viewMorph);
                inv.openOptions(p);
            } else if (dis.contains("Your hand abilities")) {
                if (!p.hasPermission("morph.toggle")) {
                    p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                    return;
                }

                morph.toggleAbilty(p);
                inv.openOptions(p);
            } else if (dis.equalsIgnoreCase("Close")) {
                p.closeInventory();
            } else if (dis.contains("Your mob sounds are")) {
                boolean sounds = c.getBoolean("sounds");
                if (c.getString("sounds") == null)
                    sounds = true;

                morph.setSoundsEnabled(p, !sounds);
                inv.openOptions(p);
            }
        }
    }

    private DisguiseType getDisguiseType(String arg) {
        for (DisguiseType type : DisguiseType.values()) {
            String t = type.toString().toUpperCase();
            if (arg.toUpperCase().equals(t)) return type;
        }
        return null;
    }
}







