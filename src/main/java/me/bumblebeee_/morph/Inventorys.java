package me.bumblebeee_.morph;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import static me.bumblebeee_.morph.MorphCommand.send;

public class Inventorys {

    MorphManager mm = new MorphManager();
    Messages msgs = new Messages();

    public static HashMap<UUID, Integer> pages = new HashMap<>();
    static HashMap<String, String> owners = new HashMap<>();

    public static void setupOwners() {
        //https://minecraft-heads.com/
        owners.put("blaze", "MHF_Blaze");
        owners.put("cave_spider", "MHF_CaveSpider");
        owners.put("chicken", "MHF_Chicken");
        owners.put("cow", "MHF_Cow");
        owners.put("creeper", "MHF_Creeper");
        owners.put("enderman", "MHF_Enderman");
        owners.put("ghast", "MHF_Ghast");
        owners.put("iron_golem", "MHF_Golem");
        owners.put("horse", "be78c4762674dde8b1a5a1e873b33f28e13e7c102b193f683549b38dc70e0");
        owners.put("magma_cube", "MHF_LavaSlime");
        owners.put("mushroom_cow", "MHF_MushroomCow");
        owners.put("ocelot", "MHF_Ocelot");
        owners.put("pig", "MHF_Pig");
        owners.put("pig_zombie", "MHF_PigZombie");
        owners.put("sheep", "MHF_Sheep");
        owners.put("skeleton", "MHF_Skeleton");
        owners.put("slime", "MHF_Slime");
        owners.put("spider", "MHF_Spider");
        owners.put("squid", "MHF_Squid");
        owners.put("villager", "MHF_Villager");
        owners.put("wither_skeleton", "MHF_WSkeleton");
        owners.put("zombie", "MHF_Zombie");
        owners.put("snowman", "MHF_Pumpkin");
        owners.put("wolf", "MHF_Wolf");
        owners.put("rabbit", "MHF_Rabbit");
        owners.put("husk", "d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121");
        owners.put("stray", "MHF_Stray");
        owners.put("polar_bear", "442123ac15effa1ba46462472871b88f1b09c1db467621376e2f71656d3fbc");
        owners.put("donkey", "63a976c047f412ebc5cb197131ebef30c004c0faf49d8dd4105fca1207edaff3");
        owners.put("Mule", "a0486a742e7dda0bae61ce2f55fa13527f1c3b334c57c034bb4cf132fb5f5f");
        owners.put("zombie_villager", "MHF_ZombieVillager");
        owners.put("vindicator", "6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173");
        owners.put("evoker", "MHF_Evoker");
        owners.put("vex", "MHF_Vex");
        owners.put("llama", "c2b1ecff77ffe3b503c30a548eb23a1a08fa26fd67cdff389855d74921368");
        owners.put("parrot", "MHF_Parrot");
        owners.put("illusioner", "512512e7d016a2343a7bff1a4cd15357ab851579f1389bd4e3a24cbeb88b");
        owners.put("guardian", "MHF_Guardian");
        owners.put("silverfish", "da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540");
        owners.put("dolphin", "8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3");
        owners.put("drowned", "MHF_Drowned");
        owners.put("cod", "7892d7dd6aadf35f86da27fb63da4edda211df96d2829f691462a4fb1cab0");
        owners.put("salmon", "8aeb21a25e46806ce8537fbd6668281cf176ceafe95af90e94a5fd84924878");
        owners.put("pufferfish", "MHF_PufferFish");
        owners.put("tropicalfish", "MHF_TropicalFish");
        owners.put("phantom", "MHF_Phantom");
        owners.put("turtle", "MHF_Turtle");
        owners.put("cat", "e9b3986e32affdb22731b687ac054a25851f8616a5a3c5ae6bb92b8ed1c9ae");
        owners.put("fox", "16db7d507389a14bbec39de6922165b32d43657bcb6aaf4b5182825b22b4");
        owners.put("panda", "d188c980aacfa94cf33088512b1b9517ba826b154d4cafc262aff6977be8a");
        owners.put("bee", "d7db9a6047d299a6945fa360299e12a13736d56f1fdfc192ec20f29cf46818c");
        owners.put("strider", "d7e4eb0fb489d6f250c607d28d672f127ebaede8e007fa6cd34e2bbc0c2fc33a");
        owners.put("zoglin", "c19b7b5e9ffd4e22b890ab778b4795b662faff2b4978bf815574e48b0e52b301");
        owners.put("hoglin", "9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75");

        owners.put("giant", "MHF_Giant");
        owners.put("ender_dragon", "ender_dragon");

        //Babies
        owners.put("baby_mushroom_cow", "MHF_MushroomCow:baby");
        owners.put("baby_cow", "MHF_Cow:baby");
        owners.put("baby_sheep", "MHF_Sheep:baby");
        owners.put("baby_pig", "MHF_Pig:baby");
        owners.put("baby_chicken", "MHF_Chicken:baby");
        owners.put("baby_wolf", "MHF_Wolf:baby");
        owners.put("baby_zombie", "MHF_Zombie:baby");
        owners.put("baby_zombie_villager", "MHF_ZombieVillager:baby");
        owners.put("baby_pig_zombie", "MHF_PigZombie:baby");
        owners.put("baby_rabbit", "MHF_Rabbit:baby");
        owners.put("baby_ocelot", "MHF_Ocelot:baby");
        owners.put("baby_villager", "MHF_Villager:baby");
        owners.put("baby_horse", "be78c4762674dde8b1a5a1e873b33f28e13e7c102b193f683549b38dc70e0:baby");
        owners.put("baby_donkey", "63a976c047f412ebc5cb197131ebef30c004c0faf49d8dd4105fca1207edaff3:baby");
        owners.put("baby_mule", "a0486a742e7dda0bae61ce2f55fa13527f1c3b334c57c034bb4cf132fb5f5f:baby");
        owners.put("baby_polar_bear", "442123ac15effa1ba46462472871b88f1b09c1db467621376e2f71656d3fbc:baby");
        owners.put("baby_husk", "d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121:baby");
    }

    public void openMorph(final Player p, int page) {
        FileConfiguration config = Morph.pl.getConfig();
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

        Bukkit.getScheduler().runTaskAsynchronously(Morph.pl, new Runnable() {
            @Override
            public void run() {

                if (owners.isEmpty())
                    setupOwners();
                File userFile = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
                String using = "none";
                if (Morph.using.containsKey(p.getUniqueId())) {
                    using = mm.getUsing(p);
                }
                List<String> mobs = fileConfig.getStringList("Mobs");
                String title;
                if (!using.equalsIgnoreCase("none")) {
                    title = msgs.getMessage("morphedTitle").replace("{mob}", using);
                } else {
                    title = msgs.getMessage("unmorphedTitle");
                }

                Inventory inv = Bukkit.getServer().createInventory(null, 36, title);

                ItemStack owner = new ItemStack(Material.PLAYER_HEAD);
                owner.setDurability((short) 3);
                SkullMeta sm = (SkullMeta) owner.getItemMeta();
                sm.setOwner(p.getName());
                sm.setDisplayName(ChatColor.DARK_PURPLE + "Click to unmorph");
                owner.setItemMeta(sm);


                int pos = 1;
                if (!mobs.isEmpty()) {
                    for (int i = ((page-1)*(27)); i < mobs.size(); i++) {
                        if (pos >= page*27)
                            continue;
                        if (!p.hasPermission("morph.bypasskill." + mobs.get(i).toLowerCase())) {
                            String mob = mobs.get(i);
                            String l1 = mobs.get(i).substring(0, 1).toUpperCase();
                            String mobName = l1 + mobs.get(i).substring(1);
                            if (mobName.split(":").length > 1) {
                                mobName = mobName.split(":")[1] + " " + mobName.split(":")[0];
                            }

                            String display = msgs.getMessage("clickToMorph").replace("{mob}", mobName.replace("_", ""));
                            inv.setItem(pos, createHead(mob, display));
                            pos++;
                        }
                    }
                }

                if (page == 1) {
                    for (String s : owners.keySet()) {
                        if (pos >= page*27)
                            continue;
                        if (p.hasPermission("morph.bypasskill." + s.toLowerCase())) {
                            String m = owners.get(s);
                            String mob = getMobName(m);
                            String l1 = mob.substring(0, 1).toUpperCase();
                            String mobName = l1 + mob.substring(1);
                            if (mobName.split("_").length > 1) {
                                mobName = mobName.split("_")[0] + " " + mobName.split("_")[1];
                            }

                            String display = msgs.getMessage("clickToMorph").replace("{mob}", mobName.replace("_", ""));
                            inv.setItem(pos, createHead(mob, display));
                            pos++;
                        }
                    }
                } else if (page == 2) {
                    int i = 0;
                    for (String s : owners.keySet()) {
                        if (i >= 26 && i < 52) {
                            if (p.hasPermission("morph.bypasskill." + s.toLowerCase())) {
                                String m = owners.get(s);
                                String mob = getMobName(m);
                                String l1 = mob.substring(0, 1).toUpperCase();
                                String mobName = l1 + mob.substring(1);
                                if (mobName.split("_").length > 1) {
                                    mobName = mobName.split("_")[0] + " " + mobName.split("_")[1];
                                }

                                String display = msgs.getMessage("clickToMorph").replace("{mob}", mobName.replace("_", ""));
                                inv.setItem(pos, createHead(mob, display));
                                pos++;
                            }
                        }
                        i++;
                    }
                } else if (page == 3) {
                    int i = 0;
                    for (String s : owners.keySet()) {
                        if (i >= 52) {
                            if (p.hasPermission("morph.bypasskill." + s.toLowerCase())) {
                                String m = owners.get(s);
                                String mob = getMobName(m);
                                String l1 = mob.substring(0, 1).toUpperCase();
                                String mobName = l1 + mob.substring(1);
                                if (mobName.split("_").length > 1) {
                                    mobName = mobName.split("_")[0] + " " + mobName.split("_")[1];
                                }

                                String display = msgs.getMessage("clickToMorph").replace("{mob}", mobName.replace("_", ""));
                                inv.setItem(pos, createHead(mob, display));
                                pos++;
                            }
                        }
                        i++;
                    }
                }
                constructInventory(inv);
                inv.setItem(0, owner);

                Bukkit.getScheduler().runTask(Morph.pl, () -> {
                    p.openInventory(inv);
                });
            }
        });
    }

    public void constructInventory(Inventory inv) {
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
        lm.setDisplayName(ChatColor.GREEN + "Previous");
        lp.setItemMeta(lm);

        ItemStack np = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta pm = np.getItemMeta();
        pm.setDisplayName(ChatColor.GREEN + "Next");
        np.setItemMeta(pm);

        ItemStack r = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta rm = r.getItemMeta();
        rm.setDisplayName(" ");
        r.setItemMeta(rm);

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

        File f = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

        boolean ability = MorphManager.toggled.contains(p.getUniqueId());
        boolean viewMorph = c.getBoolean("viewDisguise");
        boolean sound = c.getBoolean("sounds");
        boolean playerChangeView = Morph.pl.getConfig().getBoolean("canChangeView");
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

        Bukkit.getScheduler().runTask(Morph.pl, () -> {
            p.openInventory(inv);
        });
    }

    public ItemStack createHead(String mobName, String display) {
        if (mobName.equalsIgnoreCase("ender_dragon")) {
            ItemStack i = new ItemStack(Material.DRAGON_HEAD, 1);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(display);
            i.setItemMeta(im);
            return i;
        } else {
            String owner = getOwner(mobName);

            if (mobName.split(":").length > 1) {
                String[] dummy = mobName.split(":");
                mobName = dummy[1] + " " + dummy[0];
                owner = getOwner(dummy[0]);
            }
            if (owner == null) {
                return getHead(mobName, display);
            }
            return getHead(owner, display);
        }
    }

    public String getOwner(String mob) {
        return owners.get(mob.toLowerCase());
    }

    public ItemStack getHead(String owner, String display) {
        boolean baby = false;
        if (owner.split(":").length > 1) {
            owner = owner.split(":")[0];
        }

        if (!owner.split("_")[0].equalsIgnoreCase("MHF")) {
            ItemStack i = getHeadTest(owner, "Name");
            SkullMeta sm = (SkullMeta) i.getItemMeta();
            sm.setDisplayName(display);
            i.setItemMeta(sm);
            return i;
        }

        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        i.setDurability((short) 3);
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwner(owner);
        sm.setDisplayName(display);
        i.setItemMeta(sm);
        return i;
    }

    public static ItemStack getHeadTest(String playerSkullTexture, String nom) {
        playerSkullTexture = "http://textures.minecraft.net/texture/" + playerSkullTexture;
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta sk = (SkullMeta) skull.getItemMeta();
        if (!nom.equalsIgnoreCase("")) {
            sk.setDisplayName(nom);
        }

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", playerSkullTexture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = sk.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(sk, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        skull.setItemMeta(sk);

        return skull;
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
