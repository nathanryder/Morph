package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.*;
import me.bumblebeee_.morph.morphs.Morph;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryClick implements Listener {

    Inventorys inv = new Inventorys();
    Messages m = new Messages();
    String prefix = m.getMessage("prefix");

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        MorphManager morph = Main.getMorphManager();
        Player p = (Player) e.getWhoClicked();

        String mob = "";
        Morph usingMorph = Main.getMorphManager().getUsingMorph(p);
        if (usingMorph != null) {
            mob = usingMorph.toFriendly();
        }
        String rawTitle = m.getMessage("morphedTitle").replace("{mob}", mob);
        String morphedTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', rawTitle));

        String unmorphedTitle = m.getMessage("unmorphedTitle");
        if (e.getView().getTitle().startsWith(morphedTitle) ||
                e.getView().getTitle().equalsIgnoreCase(unmorphedTitle)) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

            ItemStack i = e.getCurrentItem();
            String dis = ChatColor.stripColor(i.getItemMeta().getDisplayName().toLowerCase());

            String display = ChatColor.stripColor(ChatColor
                    .translateAlternateColorCodes('&', m.getMessage("clickToMorph").replace("{mob}", "(.*)"))
                    .toLowerCase());

            if (dis.equalsIgnoreCase("Close")) {
                p.closeInventory();
            } else if (dis.contains("previous (") || dis.contains("next (")) {
                int bracket = dis.indexOf("(");
                String rawPage = dis.substring(bracket+1, bracket+2);

                int page = 0;
                try {
                    page = Integer.parseInt(rawPage);
                } catch (NumberFormatException ne) {
                    Main.pl.getLogger().severe("Failed to find a valid page number to navigate to");
                } catch (Exception err) {
                    Main.pl.getLogger().severe("Unexpected error occurred when fetching page number");
                }

                inv.openMorph(p, page);
            } else if (dis.equalsIgnoreCase("Settings")) {
                inv.openOptions(p);
            } else if (dis.equalsIgnoreCase("Click to unmorph")) {
                p.closeInventory();
                morph.unmorphPlayer(p, false, false);
            } else if (dis.matches(display)) {
                p.closeInventory();
                File userFile = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                List<String> stringList = fileConfig.getStringList("Mobs");
                List<String> players = fileConfig.getStringList("Players");
                Inventorys.pages.remove(p.getUniqueId());

//                Pattern pattern = Pattern.compile(display);
//                Matcher match = pattern.matcher(dis);
//                if (!match.find())
//                    return;

                String mobName = i.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.pl, "mobName"), PersistentDataType.STRING);
                if (mobName == null) {
                    Main.pl.getLogger().warning("Failed to find mobname when clicking item (" + mobName + ")");
                    return;
                }

                String mobType = mobName.replace(" ", "_");
                boolean baby = false;

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

                Morph type = Main.getMorphManager().getMorphType(mobType);
                if (type == null) {
                    if (Main.pl.getConfig().getBoolean("enable-players")) {
                        dis = ChatColor.stripColor(i.getItemMeta().getDisplayName());
//                        split = dis.split(" ");
                        String name = mobType;

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
                String perm = type.getMorphName().replace(" ", "_");

                if (!p.hasPermission("morph.into." + perm)) {
                    if (!p.hasPermission("morph.bypasskill." + perm)) {
                        p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                        return;
                    }
                }

                if (Main.using.containsKey(p.getUniqueId())) {
                    String using = morph.getUsing(p);
                    if (using.equalsIgnoreCase(type.getMorphName())) {
                        if (baby) {
                            if (morph.isBaby(p)) {
                                p.sendMessage(prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                                return;
                            }
                        } else {
                            if (!morph.isBaby(p)) {
                                p.sendMessage(prefix + " " + m.getMessage("alreadyMorphed", "", p.getDisplayName(), type.toFriendly(), ""));
                                return;
                            }
                        }
                    }
                }
                if (!p.hasPermission("morph.bypasskill." + perm)) {
                    String checkMob = baby ? type.getMorphName() + ":baby" : type.getMorphName();
                    if (!stringList.contains(checkMob)) {
                        if (baby) {
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorphAsPlayer", "", p.getDisplayName(), "baby " + type.toFriendly(), ""));
                        } else {
                            p.sendMessage(prefix + " " + m.getMessage("unableToMorphAsPlayer", "", p.getDisplayName(), type.toFriendly(), ""));
                        }

                        return;
                    }
                }
//                Morph.undisguiseBuffer.add(p.getUniqueId());
                DisguiseAPI.undisguiseToAll(p);
                Main.using.remove(p.getUniqueId());

                morph.morphPlayer(p, type, false, baby);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("Morph Options")) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

            ItemStack i = e.getCurrentItem();
            String dis = ChatColor.stripColor(i.getItemMeta().getDisplayName());

            File f = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            boolean viewMorph = c.getBoolean("viewDisguise");

            if (dis.contains("View own morph")) {
                boolean playerChangeView = Main.pl.getConfig().getBoolean("canChangeView");

                if (!p.hasPermission("morph.changeview")) {
                    p.sendMessage(prefix + " " + m.getMessage("noPermissions"));
                    return;
                }
                if (!playerChangeView) {
                    p.sendMessage(m.getMessage("unableToChangeView"));
                    return;
                }

                if (c.getString("viewDisguise") == null)
                    viewMorph = Main.pl.getConfig().getBoolean("viewSelfDisguise");

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

}







