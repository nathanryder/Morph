package me.bumblebeee_.morph;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.AgeableWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.ZombieWatcher;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MorphManager {

    Messages m = new Messages();
    String prefix = m.getMessage("prefix");
    public static List<UUID> toggled = new ArrayList<>();
    public static List<UUID> viewMorphBuffer = new ArrayList<>();
    public static List<UUID> soundDisabled = new ArrayList<>();
    public static Map<UUID, Map<String, Integer>> typeCooldown = new HashMap();
    public static Map<UUID, Integer> morphTimeout = new HashMap();

    public void toggleAbilty(Player p) {
        if (toggled.contains(p.getUniqueId())) {
            toggled.remove(p.getUniqueId());
            p.sendMessage(prefix + " " + m.getMessage("abilityToggledOn"));
        } else {
            toggled.add(p.getUniqueId());
            p.sendMessage(prefix + " " + m.getMessage("abilityToggledOff"));
        }
    }

    public boolean canSeeOwnMorph(Player p) {
        File userFile = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
        return fileConfig.getBoolean("viewDisguise");
    }

    public void setViewMorph(Player p, boolean ownView) {
        File userFile = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);

        fileConfig.set("viewDisguise", ownView);
        try {
            fileConfig.save(userFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ownView)
            p.sendMessage(prefix + " " + m.getMessage("changeViewSuccess").replace("%status%", "now"));
        else
            p.sendMessage(prefix + " " + m.getMessage("changeViewSuccess").replace("%status%", "no longer"));

        if (DisguiseAPI.isDisguised(p)) {
            boolean baby = Morph.using.get(p.getUniqueId()).split(" ")[0].equalsIgnoreCase("baby");
            viewMorphBuffer.add(p.getUniqueId());
            morphPlayer(p, DisguiseAPI.getDisguise(p).getType(), true, baby);
        }
    }

    public void setSoundsEnabled(Player p, boolean status) {
        File userFile = new File(Morph.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);

        fileConfig.set("sounds", status);
        try {
            fileConfig.save(userFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (status) {
            p.sendMessage(prefix + " " + m.getMessage("changeSoundVolSuccess").replace("%statusColor%", ChatColor.GREEN + "enabled"));
            soundDisabled.remove(p.getUniqueId());
        } else {
            p.sendMessage(prefix + " " + m.getMessage("changeSoundVolSuccess").replace("%statusColor%", ChatColor.RED + "disabled"));
            soundDisabled.add(p.getUniqueId());
        }
    }

    public void morphPlayer(final Player p, DisguiseType type, boolean silent, boolean baby) {
        if (typeCooldown.containsKey(p.getUniqueId())) {
            Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
            if (cooldown.containsKey(type.toString().toLowerCase())) {
                int time = cooldown.get(type.toString().toLowerCase());
                p.sendMessage(prefix + " " + m.getMessage("morphOnCooldown", "", p.getDisplayName(), type.toReadable().toLowerCase(), time));
                return;
            }
        }

        Morph.undisguiseBuffer.add(p.getUniqueId());

        if (DisguiseAPI.isDisguised(p)) {
            DisguiseAPI.undisguiseToAll(p);
        }
        Plugin pl = Morph.pl;
        MobDisguise mob = new MobDisguise(DisguiseType.valueOf(type.toString()));

        //setViewSelfDisguise doesn't exist in LibsDisguises 1.7.10
        boolean viewSelf = Morph.pl.getConfig().getBoolean("viewSelfDisguise");
        if (!Bukkit.getVersion().contains("1.7.10")) {
            boolean canChangeView = Morph.pl.getConfig().getBoolean("canChangeView");
            if (canChangeView) {
                File f = new File(pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);

                if (c.getString("viewDisguise") != null) {
                    viewSelf = c.getBoolean("viewDisguise");
                }
            }
            mob.setViewSelfDisguise(viewSelf);
        }

        if (!Bukkit.getVersion().contains("1.7.10")) {
            mob.setEntity(p.getPlayer());
            if (baby) {
                if (mob.getWatcher() instanceof AgeableWatcher) {
                    ((AgeableWatcher) mob.getWatcher()).setBaby(true);
                } else if (mob.getWatcher() instanceof ZombieWatcher) {
                    ((ZombieWatcher) mob.getWatcher()).setBaby(true);
                }
            }
        }

        DisguiseAPI.disguiseToAll(p, mob);

        if (baby)
            Morph.using.put(p.getUniqueId(), "baby " + type.toString().toLowerCase());
        else
            Morph.using.put(p.getUniqueId(), type.toString().toLowerCase());

        String using = getUsing(p);
        if (Morph.health) {
            p.setHealthScale(20.0);
            p.setMaxHealth(20.0);
        }
        if (!p.hasPermission("morph.fly")) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

        if (using.equalsIgnoreCase("bat")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
            if (Morph.health) {
                p.setHealthScale(6);
                p.setMaxHealth(6);
            }
        } else if (using.equalsIgnoreCase("vex")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
                if (Morph.health) {
                    p.setHealthScale(14);
                    p.setMaxHealth(14);
                }
            }
        } else if (using.equalsIgnoreCase("wither")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        } else if (using.equalsIgnoreCase("blaze")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
            FlagWatcher watcher = DisguiseAPI.getDisguise(p).getWatcher();
            watcher.setBurning(true);
        } else if (using.equalsIgnoreCase("ghast")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
                if (Morph.health) {
                    p.setHealthScale(10);
                    p.setMaxHealth(10);
                }
            }
            if (Morph.health) {
                p.setHealthScale(10);
                p.setMaxHealth(10);
            }
        } else if (using.equalsIgnoreCase("ocelot")) {
            if (Morph.health) {
                p.setHealthScale(6);
                p.setMaxHealth(6);
            }
        } else if (using.equalsIgnoreCase("sheep")) {
            if (Morph.health) {
                p.setHealthScale(4);
                p.setMaxHealth(4);
            }
        } else if (using.equalsIgnoreCase("silverfish")) {
            if (Morph.health) {
                p.setHealthScale(4);
                p.setMaxHealth(4);
            }
        } else if (using.equalsIgnoreCase("snowman")) {
            if (Morph.health) {
                p.setHealthScale(2);
                p.setMaxHealth(2);
            }
        } else if (using.equalsIgnoreCase("wolf")) {
            if (Morph.health) {
                p.setHealthScale(4);
                p.setMaxHealth(4);
            }
        } else if (using.equalsIgnoreCase("pig")) {
            if (Morph.health) {
                p.setHealthScale(5);
                p.setMaxHealth(5);
            }
        } else if (using.equalsIgnoreCase("cow")) {
            if (Morph.health) {
                p.setHealthScale(5);
                p.setMaxHealth(5);
            }
        } else if (using.equalsIgnoreCase("cave_spider")) {
            if (Morph.health) {
                p.setHealthScale(6);
                p.setMaxHealth(6);
            }
        } else if (using.equalsIgnoreCase("spider")) {
            if (Morph.health) {
                p.setHealthScale(8);
                p.setMaxHealth(8);
            }
        } else if (using.equalsIgnoreCase("chicken")) {
            if (Morph.health) {
                p.setHealthScale(2);
                p.setMaxHealth(2);
            }
        } else if (using.equalsIgnoreCase("ender_dragon")) {
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        } else if (using.equalsIgnoreCase("rabbit")) {
            if (Morph.health) {
                p.setHealthScale(3);
                p.setMaxHealth(3);
            }
        } else if (using.equalsIgnoreCase("guardian")) {
            if (Morph.health) {
                p.setHealthScale(30);
                p.setMaxHealth(30);
            }
        } else if (using.equalsIgnoreCase("endermite")) {
            if (Morph.health) {
                p.setHealthScale(8);
                p.setMaxHealth(8);
            }
        } else if (using.equalsIgnoreCase("shulker")) {
            if (Morph.health) {
                p.setHealthScale(30);
                p.setMaxHealth(30);
            }
        } else if (using.equalsIgnoreCase("polar_bear")) {
            if (Morph.health) {
                p.setHealthScale(30);
                p.setMaxHealth(30);
            }
        } else if (using.equalsIgnoreCase("vindicator")) {
            if (Morph.health) {
                p.setHealthScale(24);
                p.setMaxHealth(24);
            }
        } else if (using.equalsIgnoreCase("evoker")) {
            if (Morph.health) {
                p.setHealthScale(24);
                p.setMaxHealth(24);
            }
        } else if (using.equalsIgnoreCase("parrot")) {
            if (Morph.health) {
                p.setHealthScale(6);
                p.setMaxHealth(6);
            }
            if (pl.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        } else if (using.equalsIgnoreCase("illusioner")) {
            if (Morph.health) {
                p.setHealthScale(32);
                p.setMaxHealth(32);
            }
        }

        if (!silent) {
            if (baby)
                p.sendMessage(prefix + " " + m.getMessage("youHaveMorphed", "", p.getDisplayName(), "baby " + type.toReadable().toLowerCase(), ""));
            else
                p.sendMessage(prefix + " " + m.getMessage("youHaveMorphed", "", p.getDisplayName(), type.toReadable().toLowerCase(), ""));
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Morph.pl, new Runnable() {
                World w = p.getWorld();
                final Location loc = p.getLocation();

                int i = 0;

                public void run() {
                    if (i < 3) {
                        i++;
                        if (Morph.pl.getConfig().getBoolean("morph-particle")) {
                            if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                                p.getWorld().playEffect(p.getLocation().add(0, 1, 0), Effect.EXPLOSION_LARGE, 50);
                        }
                        if (Morph.pl.getConfig().getBoolean("morph-sound")) {
                            if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                                w.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
                        }

                    }
                }
            }, 0, 10);
        }

        Morph.undisguiseBuffer.remove(p.getUniqueId());
        final String typeStr = type.toString().toLowerCase();
        final int timeLimit = Morph.pl.getConfig().getInt(typeStr + ".morph-time");

        if (timeLimit > 0 && !p.hasPermission("morph.bypasstime." + typeStr)) {
            morphTimeout.put(p.getUniqueId(), timeLimit+1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    int time = morphTimeout.get(p.getUniqueId())-1;
                    morphTimeout.remove(p.getUniqueId());

                    if (time == 0) {
                        unmorphPlayer(p, false, true);
                        ManaManager.ab.sendActionbar(p, "");
                        cancel();
                        return;
                    }

                    morphTimeout.put(p.getUniqueId(), time);
                    ManaManager.ab.sendActionbar(p, m.getMessage("timeLeftAsMorph", "", "", typeStr, time));
                }
            }.runTaskTimer(Morph.pl, 0, 20);

        }
    }

    public void unmorphPlayer(final Player p, boolean staff, boolean time) {
        if (!DisguiseAPI.isDisguised(p)) {
            p.sendMessage(prefix + " " +  m.getMessage("notMorphedAsAnything"));
            return;
        }

        if (Morph.health) {
            p.setHealthScale(20.0);
            p.setMaxHealth(20.0);
        }
        if (!p.hasPermission("morph.fly")) {
            p.setAllowFlight(false);
            p.setFlying(false);
        }
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

        final String type = DisguiseAPI.getDisguise(p).getType().toString().toLowerCase();
        Morph.using.remove(p.getUniqueId());
        DisguiseAPI.undisguiseToAll(p);

        if (staff) {
            p.sendMessage(prefix + " " + m.getMessage("unmorphedByStaff", "", p.getDisplayName(), "", ""));
        } else if (time) {
            p.sendMessage(prefix + " " + m.getMessage("unmorphedByTime", "", p.getDisplayName(), "", ""));
        } else {
            p.sendMessage(prefix + " " + m.getMessage("morphReversed", "", p.getDisplayName(), "", ""));
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Morph.pl, new Runnable() {
            World w = p.getWorld();
            final Location loc = p.getLocation();

            int i = 0;

            public void run() {
                if (i < 3) {
                    i++;
                    if (Morph.pl.getConfig().getBoolean("unmorph-particle")) {
                        if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                            p.getWorld().playEffect(p.getLocation().add(0, 1, 0), Effect.EXPLOSION_LARGE, 50);
                    }
                    if (Morph.pl.getConfig().getBoolean("unmorph-sound")) {
                        if (!ManaManager.version.equalsIgnoreCase("v1_8_R3")) {
                            if (!Bukkit.getVersion().contains("1.7.10"))
                                w.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
                        }
                    }

                }
            }
        }, 0, 10);

        int morphCooldown = Morph.pl.getConfig().getInt(type + ".morph-cooldown");
        if (morphCooldown > 0) {
            if (typeCooldown.containsKey(p.getUniqueId())) {
                Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
                typeCooldown.remove(p.getUniqueId());

                cooldown.put(type, morphCooldown);
                typeCooldown.put(p.getUniqueId(), cooldown);
            } else {
                Map<String, Integer> cooldown = new HashMap<>();
                cooldown.put(type, morphCooldown);
                typeCooldown.put(p.getUniqueId(), cooldown);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
                    int time = cooldown.get(type)-1;
                    cooldown.remove(type);

                    if (time != 0)
                        cooldown.put(type, time);
                    else if (time == 0) {
                        cancel();
                        return;
                    }

                    typeCooldown.put(p.getUniqueId(), cooldown);
                }
            }.runTaskTimer(Morph.pl, 20, 20);
        }
    }

    public boolean isAllowBaby(String mob) {
        List<String> babies = new ArrayList<>();
        babies.add("mushroom cow");
        babies.add("cow");
        babies.add("sheep");
        babies.add("pig");
        babies.add("chicken");
        babies.add("wolf");
        babies.add("zombie");
        babies.add("zombie villager");
        babies.add("pig zombie");
        babies.add("rabbit");
        babies.add("ocelot");
        babies.add("villager");
        babies.add("horse");
        babies.add("donkey");
        babies.add("mule");
        babies.add("polar bear");
        babies.add("husk");

        return babies.contains(mob.toLowerCase());
    }

    public String getUsing(Player p) {
        if (!Morph.using.containsKey(p.getUniqueId()))
            return null;
        String using;
        String[] data = Morph.using.get(p.getUniqueId()).split(" ");
        if (data.length > 1)
            using = data[1];
        else
            using = data[0];
        return using;
    }

    public boolean isBaby(Player p) {
        if (!Morph.using.containsKey(p.getUniqueId()))
            return false;

        if (!DisguiseAPI.isDisguised(p))
            return false;

        Disguise d = DisguiseAPI.getDisguise(p);


        if (d.getWatcher() instanceof AgeableWatcher) {
            return ((AgeableWatcher) d.getWatcher()).isBaby();
        } else if (d.getWatcher() instanceof ZombieWatcher) {
            return ((ZombieWatcher) d.getWatcher()).isBaby();
        }
        return false;
    }

    public Sound playSound(Player p) {
        if (!Morph.using.containsKey(p.getUniqueId()))
            return null;
        String mob = Morph.using.get(p.getUniqueId());

        switch (mob) {
            case "cow":
                return Sound.ENTITY_COW_AMBIENT;
            case "vex":
                return Sound.ENTITY_VEX_AMBIENT;
            case "horse":
                return Sound.ENTITY_HORSE_AMBIENT;
            case "enderman":
                return Sound.ENTITY_ENDERMEN_AMBIENT;
            case "villager":
                return Sound.ENTITY_VILLAGER_AMBIENT;
            case "stray":
                return Sound.ENTITY_STRAY_AMBIENT;
            case "iron_golem":
                return Sound.ENTITY_IRONGOLEM_ATTACK;
            case "blaze":
                return Sound.ENTITY_BLAZE_AMBIENT;
            case "skeleton":
                return Sound.ENTITY_SKELETON_AMBIENT;
            case "chicken":
                return Sound.ENTITY_CHICKEN_AMBIENT;
            case "cave_spider":
                return Sound.ENTITY_SPIDER_AMBIENT;
            case "pig_zombie":
                return Sound.ENTITY_ZOMBIE_PIG_AMBIENT;
            case "mushroom_cow":
                return Sound.ENTITY_COW_AMBIENT;
            case "slime":
                return Sound.ENTITY_SLIME_SQUISH;
            case "polar_bear":
                return Sound.ENTITY_POLAR_BEAR_AMBIENT;
            case "zombie":
                return Sound.ENTITY_ZOMBIE_AMBIENT;
            case "sheep":
                return Sound.ENTITY_SHEEP_AMBIENT;
            case "spider":
                return Sound.ENTITY_SPIDER_AMBIENT;
            case "wither_skeleton":
                return Sound.ENTITY_WITHER_SKELETON_AMBIENT;
            case "husk":
                return Sound.ENTITY_HUSK_AMBIENT;
            case "magma_cube":
                return Sound.ENTITY_MAGMACUBE_SQUISH;
            case "creeper":
                return Sound.ENTITY_CREEPER_PRIMED;
            case "squid":
                return Sound.ENTITY_SQUID_AMBIENT;
            case "pig":
                return Sound.ENTITY_ZOMBIE_PIG_AMBIENT;
            case "ghast":
                return Sound.ENTITY_GHAST_AMBIENT;
            case "ender_dragon":
                return Sound.ENTITY_ENDERDRAGON_AMBIENT;
            case "snowman":
                return Sound.ENTITY_SNOWMAN_AMBIENT;
        }
        return null;
    }

}
