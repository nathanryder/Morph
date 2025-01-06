package me.bumblebeee_.morph.managers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.morphs.Morph;
import me.bumblebeee_.morph.utils.GUIComparator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static me.bumblebeee_.morph.MorphCommand.send;

public class Inventorys {

    Messages msgs = new Messages();

    public static HashMap<UUID, Integer> pages = new HashMap<>();
    static Map<String, String> owners = new TreeMap<>(new GUIComparator());

    public static void addHead(String mob, String headId) {
        owners.put(mob, headId);
    }

    public void openMorph(final Player p, int page) {
        FileConfiguration config = Main.pl.getConfig();
        if (config.getBoolean("disableGUI")) {
            p.sendMessage(msgs.getMessage("GUIdisabled"));
            return;
        }

        if (!(config.getStringList("enabled-worlds").contains(p.getWorld().getName()))) {
            if (!(config.getStringList("enabled-worlds").contains("<all>"))) {
                send(p, msgs.getMessage("prefix") + " " + msgs.getMessage("disableInThisWorld"));
                return;
            }
        }

        p.closeInventory();
        p.sendMessage(msgs.getMessage("openingInventory"));

        Bukkit.getScheduler().runTaskAsynchronously(Main.pl, new Runnable() {
            @Override
            public void run() {

                File userFile = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                String using = "none";
                if (Main.using.containsKey(p.getUniqueId())) {
                    using = Main.getMorphManager().getUsing(p);
                }

                List<String> mobs = fileConfig.getStringList("Mobs");
                List<String> players = fileConfig.getStringList("Players");
                String title;
                if (!using.equalsIgnoreCase("none")) {
                    title = msgs.getMessage("morphedTitle").replace("{mob}", Main.getMorphManager().getMorphType(using).toFriendly());
                } else {
                    title = msgs.getMessage("unmorphedTitle");
                }

                Inventory inv = Bukkit.getServer().createInventory(null, 36, title);
                List<String> allMorphs = new ArrayList<>();
                for (int i = 0; i < mobs.size(); i++) {
                    String mob = mobs.get(i);
                    if (p.hasPermission("morph.bypasskill." + mob.toLowerCase())) {
                        continue;
                    }

                    allMorphs.add(mob);
                }
                for (int i = 0; i < owners.size(); i++) {
                    String mob = (String) owners.keySet().toArray()[i];
                    if (!p.hasPermission("morph.bypasskill." + mob.toLowerCase())) {
                        continue;
                    }

                    allMorphs.add(mob);
                }
                for (int i = 0; i < players.size(); i++) {
                    String player = players.get(i);
                    allMorphs.add("player:" + player);
                }

                boolean lastPage = false;
                int pos = 1;
                for (int i = ((page-1)*(26)); i < ((page)*26); i++) {
                    if (i >= allMorphs.size()) {
                        lastPage = true;
                        break;
                    }

                    String mob = allMorphs.get(i);
                    if (mob.startsWith("player:")) {
                        String player = mob.split(":")[1];
                        String display = msgs.getMessage("clickToMorph").replace("{mob}", player);
                        inv.setItem(pos, createHead("player:" + player, display));

                        continue;
                    }

                    ItemStack head = createMobHead(mob);
                    inv.setItem(pos, head);
                    pos++;
                }

                int nextPage = page+1;
                int previousPage = page-1;
                if (lastPage) {
                    nextPage = 1;
                }
                if (page == 1) {
                    previousPage = (int) Math.ceil((double) allMorphs.size() /26);
                }

                constructInventory(inv, p.getUniqueId(), nextPage, previousPage);

                Bukkit.getScheduler().runTask(Main.pl, () -> {
                    p.openInventory(inv);
                });
            }
        });
    }

    public ItemStack createMobHead(String mob) {
        Morph morphType = Main.getMorphManager().getMorphType(mob.split(":")[0]);
        if (morphType == null) {
            return null;
        }

        String mobName = morphType.toFriendly();
        if (mob.split(":").length > 1) {
            mobName = "Baby " + mobName.split(":")[0];
        }

        String display = msgs.getMessage("clickToMorph").replace("{mob}", mobName.replace("_", ""));
        return createHead(mob, display);
    }

    public void constructInventory(Inventory inv, UUID ownerUUID, int nextPage, int previousPage) {
        ItemStack settings = new ItemStack(Material.REDSTONE);
        ItemMeta sm = settings.getItemMeta();
        sm.setDisplayName(ChatColor.AQUA + "Settings");
        sm.setLore(new ArrayList<>(Collections.singleton(ChatColor.GRAY + "Click to view your settings")));
        settings.setItemMeta(sm);

        ItemStack close = new ItemStack(Material.NETHER_STAR);
        ItemMeta cm = close.getItemMeta();
        cm.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(cm);

        ItemStack lp = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta lm = lp.getItemMeta();
        lm.setDisplayName(ChatColor.GREEN + "Previous (" + previousPage + ")");
        lp.setItemMeta(lm);

        ItemStack np = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta pm = np.getItemMeta();
        pm.setDisplayName(ChatColor.GREEN + "Next (" + nextPage + ")");
        np.setItemMeta(pm);

        ItemStack r = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta rm = r.getItemMeta();
        rm.setDisplayName(" ");
        r.setItemMeta(rm);

        ItemStack owner = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) owner.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Click to unmorph");
        owner.setItemMeta(meta);
//        ItemStack owner = createHead("player:" + ownerUUID, ChatColor.DARK_PURPLE + "Click to unmorph");
        inv.setItem(0, owner);

        for (int i = 27; i < 36; i++) {
            if (i == 27) {
                inv.setItem(i, lp);
            } else if (i == 35) {
                inv.setItem(i, np);
            } else if (i == 31) {
                inv.setItem(i, close);
            } else if (i == 29) {
                inv.setItem(i, settings);
            } else {
                inv.setItem(i, r);
            }
        }
    }

    public void openOptions(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(p, 36, "Morph Options");

        File f = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

        boolean ability = Main.getMorphManager().toggled.contains(p.getUniqueId());
        boolean viewMorph = c.getBoolean("viewDisguise");
        boolean sound = c.getBoolean("sounds");
        boolean playerChangeView = Main.pl.getConfig().getBoolean("canChangeView");
        if (c.getString("viewDisguise") == null)
            viewMorph = playerChangeView;
        if (c.getString("sounds") == null)
            sound = true;

        ItemStack bottomFiller = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta bm = bottomFiller.getItemMeta();
        bm.setDisplayName(" ");
        bottomFiller.setItemMeta(bm);

        ItemStack close = new ItemStack(Material.NETHER_STAR);
        ItemMeta cm = close.getItemMeta();
        cm.setDisplayName(ChatColor.RED + "Close");
        close.setItemMeta(cm);

        ItemStack filler = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta fm = filler.getItemMeta();
        fm.setDisplayName(" ");
        filler.setItemMeta(fm);

        ItemStack view = new ItemStack(Material.PLAYER_HEAD);
        view.setDurability((short) 3);
        SkullMeta sm = (SkullMeta) view.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(ChatColor.GRAY + "View own morph is " + (viewMorph ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
        sm.setLore(new ArrayList<>(Collections.singleton(ChatColor.GRAY + "Do you want to see your own morph?")));
        view.setItemMeta(sm);

        ItemStack ab = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta abm = ab.getItemMeta();
        abm.setDisplayName(ChatColor.GRAY + "Your hand abilities are " + (ability ? ChatColor.RED + "disabled" : ChatColor.GREEN + "enabled"));
        abm.setLore(new ArrayList<>(Collections.singleton(ChatColor.GRAY + "Left click to use your abilities!")));
        ab.setItemMeta(abm);

        ItemStack si = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta sim = si.getItemMeta();
        sim.setDisplayName(ChatColor.GRAY + "Your mob sounds are " + (sound ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
        sim.setLore(new ArrayList<>(Collections.singleton(ChatColor.GRAY + "Hold shift for 1 second to make a noise!")));
        si.setItemMeta(sim);

        for (int i = 0; i < 36; i++) {
            if (i > 26 && i < 36)
                inv.setItem(i, bottomFiller);
            else
                inv.setItem(i, filler);
        }

        if (playerChangeView) {
            inv.setItem(11, view);
            inv.setItem(13, ab);
        } else {
            inv.setItem(11, ab);
        }
        inv.setItem(15, si);
        inv.setItem(31, close);

        Bukkit.getScheduler().runTask(Main.pl, () -> {
            p.openInventory(inv);
        });
    }

    public ItemStack createHead(String mobName, String display) {
        if (mobName.equalsIgnoreCase("ender_dragon")) {
            ItemStack i = new ItemStack(Material.DRAGON_HEAD, 1);
            SkullMeta sm = (SkullMeta) i.getItemMeta();
            sm.getPersistentDataContainer().set(new NamespacedKey(Main.pl, "mobName"), PersistentDataType.STRING, mobName);
            sm.setDisplayName(display);
            i.setItemMeta(sm);

            return i;
        } else if (mobName.contains("player:")) {
            String playerName = mobName.split(":")[1];

            ItemStack i = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) i.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.pl, "mobName"), PersistentDataType.STRING, playerName);
            meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(playerName));
            meta.setDisplayName(display);
            i.setItemMeta(meta);

            return i;
        } else {
            String owner = getOwner(mobName);

            if (mobName.split(":").length > 1) {
                String[] dummy = mobName.split(":");
                mobName = dummy[1] + " " + dummy[0];
                owner = getOwner(dummy[0]);
            }

            if (owner == null) {
                return getHead(mobName, display, mobName);
            }
            return getHead(owner, display, mobName);
        }
    }

    public String getOwner(String mob) {
        return owners.get(mob.toLowerCase());
    }

    public ItemStack getHead(String owner, String display, String mobName) {
        if (owner.split(":").length > 1) {
            owner = owner.split(":")[0];
        }

        ItemStack i = getHeadTest(owner);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.getPersistentDataContainer().set(new NamespacedKey(Main.pl, "mobName"), PersistentDataType.STRING, mobName);
        sm.setDisplayName(display);
        i.setItemMeta(sm);
        return i;
    }

    public static ItemStack getHeadTest(String playerSkullTexture) {
        String url = "http://textures.minecraft.net/texture/" + playerSkullTexture;

        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile);
        head.setItemMeta(meta);

        return head;
    }

    public String getMobName(String owner) {
        boolean baby = false;
        if (owner.split(":").length > 1)
            baby = owner.split(":")[1].equalsIgnoreCase("baby");

        for (String v : owners.keySet()) {
            String got = owners.get(v);
            if (owner.equalsIgnoreCase(got)) {
                if (baby) {
                    return v.split(":")[0];
                } else {
                    return v;
                }
            }
        }
        return null;
    }

}
