package me.bumblebeee_.morph.managers;

import lombok.Getter;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.morphs.Flyable;
import me.bumblebeee_.morph.morphs.Morph;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.DisguiseConfig;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.AgeableWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.ZombieWatcher;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MorphManager {

    Messages m = new Messages();
    public @Getter List<UUID> toggled = new ArrayList<>();
    public @Getter List<UUID> viewMorphBuffer = new ArrayList<>();
    public @Getter List<UUID> soundDisabled = new ArrayList<>();
    public @Getter Map<UUID, Map<String, Integer>> typeCooldown = new HashMap();
    public @Getter Map<UUID, Integer> morphTimeout = new HashMap();
    public @Getter List<UUID> removeFromTimeout = new ArrayList<>();

    private @Getter Map<String, Morph> morphs = new HashMap<>();

    public void registerMorph(Morph morph) {
        if (!morph.isEnabled())
            return;

        morphs.put(morph.getMorphName(), morph);
    }

    public Morph getMorphType(String type) {
        Morph find = Main.getMorphManager().getMorphs().get(type);
        if (find == null) {
            for (String name : Main.getMorphManager().getMorphs().keySet()) {
                Morph morph = Main.getMorphManager().getMorphs().get(name);

                if (name.replace("_", "").equalsIgnoreCase(type)) {
                    find = morph;
                    break;
                }
            }
        }

        return find;
    }

    public void morphPlayer(final Player p, Morph morphType, boolean silent, boolean baby) {
        String prefix = m.getMessage("prefix");
        if (!morphType.isEnabled()) {
            p.sendMessage(prefix + " " + m.getMessage("mobDisabled"));

            if (Main.health) {
                p.resetMaxHealth();
            }
            return;
        }

        if (typeCooldown.containsKey(p.getUniqueId())) {
            Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
            if (cooldown.containsKey(morphType.getMorphName())) {
                int time = cooldown.get(morphType.getMorphName());
                p.sendMessage(prefix + " " + m.getMessage("morphOnCooldown", "", p.getDisplayName(), morphType.toFriendly(), time));
                return;
            }
        }

        Main.undisguiseBuffer.add(p.getUniqueId());
        if (DisguiseAPI.isDisguised(p)) {
            DisguiseAPI.undisguiseToAll(p);
        }
        Plugin pl = Main.pl;
        MobDisguise mob = new MobDisguise(morphType.getDisguiseType());
        mob.setNotifyBar(DisguiseConfig.NotifyBar.NONE);

        //setViewSelfDisguise doesn't exist in LibsDisguises 1.7.10
        boolean viewSelf = Main.pl.getConfig().getBoolean("viewSelfDisguise");
        if (!Bukkit.getVersion().contains("1.7.10")) {
            boolean canChangeView = Main.pl.getConfig().getBoolean("canChangeView");
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
            Main.using.put(p.getUniqueId(), "baby " + morphType.getMorphName());
        else
            Main.using.put(p.getUniqueId(), morphType.getMorphName());

        p.setAllowFlight(false);
        p.setFlying(false);
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

        if (Main.health) {
            int health = morphType.getHealth();
            p.setMaxHealth(health);
        }

        if (morphType instanceof Flyable) {
            if (Config.MOB_CONFIG.getConfig().getBoolean("flying")) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        }

        AttributeInstance scale = p.getAttribute(Attribute.GENERIC_SCALE);
        if (scale != null && scale.getBaseValue() != 0.0) {
            scale.setBaseValue(morphType.getScale());
        }

        morphType.initMorph(p);

        if (!silent) {
            if (baby)
                p.sendMessage(prefix + " " + m.getMessage("youHaveMorphed", "", p.getDisplayName(), "baby " + morphType.toFriendly(), ""));
            else
                p.sendMessage(prefix + " " + m.getMessage("youHaveMorphed", "", p.getDisplayName(), morphType.toFriendly(), ""));

            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.pl, new Runnable() {
                World w = p.getWorld();
                final Location loc = p.getLocation();

                int i = 0;

                public void run() {
                    if (i < 3) {
                        i++;
                        if (Main.pl.getConfig().getBoolean("morph-particle")) {
                            if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                                p.getWorld().playEffect(p.getLocation().add(0, 1, 0), Effect.BLAZE_SHOOT, 50);
                        }
                        if (Main.pl.getConfig().getBoolean("morph-sound")) {
                            if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                                w.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
                        }

                    }
                }
            }, 0, 10);
        }

        Main.undisguiseBuffer.remove(p.getUniqueId());
        final String typeStr = morphType.getMorphName();
        final int timeLimit = morphType.getMorphTime();

        if (timeLimit > 0 && !p.hasPermission("morph.bypasstime." + typeStr)) {
            morphTimeout.put(p.getUniqueId(), timeLimit+1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!morphTimeout.containsKey(p.getUniqueId())) {
                        cancel();
                        return;
                    }

                    if (DisguiseAPI.getDisguise(p) == null) {
                        cancel();
                        return;
                    }

                    if (!typeStr.equals(DisguiseAPI.getDisguise(p).getType().toString().toLowerCase())) {
                        cancel();
                        return;
                    }

                    int time = morphTimeout.get(p.getUniqueId())-1;
                    morphTimeout.remove(p.getUniqueId());

                    if (removeFromTimeout.contains(p.getUniqueId())) {
                        ManaManager.ab.sendActionbar(p, "");
                        removeFromTimeout.remove(p.getUniqueId());
                        cancel();
                        return;
                    }

                    if (time == 0) {
                        unmorphPlayer(p, false, true);
                        ManaManager.ab.sendActionbar(p, "");
                        cancel();
                        return;
                    }

                    morphTimeout.put(p.getUniqueId(), time);

                    if (morphType instanceof Flyable) {
                        if (time <= 30) {
                            int minutes = time / 60;
                            int seconds = time % 60;
                            String disMin = (minutes < 10 ? "0" : "") + minutes;
                            String disSec = (seconds < 10 ? "0" : "") + seconds;
                            ManaManager.ab.sendActionbar(p, m.getMessage("timeLeftAsMorph", typeStr, morphType.toFriendly(), disSec));
                        }
                    } else {
                        int minutes = time / 60;
                        int seconds = time % 60;
                        String disMin = (minutes < 10 ? "0" : "") + minutes;
                        String disSec = (seconds < 10 ? "0" : "") + seconds;
                        ManaManager.ab.sendActionbar(p, m.getMessage("timeLeftAsMorph", morphType.toFriendly(), disMin, disSec));
                    }
                }
            }.runTaskTimer(Main.pl, 0, 20);

        }
    }

    public void unmorphPlayer(final Player p, boolean staff, boolean time) {
        String prefix = m.getMessage("prefix");
        if (!DisguiseAPI.isDisguised(p)) {
            p.sendMessage(prefix + " " +  m.getMessage("notMorphedAsAnything"));
            return;
        }

        if (Main.health) {
            p.resetMaxHealth();
        }

        AttributeInstance scale = p.getAttribute(Attribute.GENERIC_SCALE);
        if (scale != null) {
            scale.setBaseValue(1);
        }

        p.setAllowFlight(false);
        p.setFlying(false);
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

//        final String type = DisguiseAPI.getDisguise(p).getType().toString().toLowerCase();
        final Morph type = getUsingMorph(p);
        Main.using.remove(p.getUniqueId());
        DisguiseAPI.undisguiseToAll(p);

        if (staff) {
            p.sendMessage(prefix + " " + m.getMessage("unmorphedByStaff", "", p.getDisplayName(), "", ""));
        } else if (time) {
            p.sendMessage(prefix + " " + m.getMessage("unmorphedByTime", "", p.getDisplayName(), "", ""));
        } else {
            p.sendMessage(prefix + " " + m.getMessage("morphReversed", "", p.getDisplayName(), "", ""));
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.pl, new Runnable() {
            World w = p.getWorld();
            final Location loc = p.getLocation();

            int i = 0;

            public void run() {
                if (i < 3) {
                    i++;
                    if (Main.pl.getConfig().getBoolean("unmorph-particle")) {
                        if (!ManaManager.version.equalsIgnoreCase("v1_8_R3"))
                            p.getWorld().playEffect(p.getLocation().add(0, 1, 0), Effect.BLAZE_SHOOT, 50);
                    }
                    if (Main.pl.getConfig().getBoolean("unmorph-sound")) {
                        if (!ManaManager.version.equalsIgnoreCase("v1_8_R3")) {
                            if (!Bukkit.getVersion().contains("1.7.10"))
                                w.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
                        }
                    }

                }
            }
        }, 0, 10);

        if (morphTimeout.get(p.getUniqueId()) != null)
            removeFromTimeout.add(p.getUniqueId());

        if (type == null)
            return;

        int morphCooldown = type.getMorphCooldown();
        if (morphCooldown > 0) {
            if (typeCooldown.containsKey(p.getUniqueId())) {
                Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
                typeCooldown.remove(p.getUniqueId());

                cooldown.put(type.getMorphName(), morphCooldown);
                typeCooldown.put(p.getUniqueId(), cooldown);
            } else {
                Map<String, Integer> cooldown = new HashMap<>();
                cooldown.put(type.getMorphName(), morphCooldown);
                typeCooldown.put(p.getUniqueId(), cooldown);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    Map<String, Integer> cooldown = typeCooldown.get(p.getUniqueId());
                    int time = cooldown.get(type.getMorphName())-1;
                    cooldown.remove(type.getMorphName());

                    if (time != 0)
                        cooldown.put(type.getMorphName(), time);
                    else {
                        cancel();
                        return;
                    }

                    typeCooldown.put(p.getUniqueId(), cooldown);
                }
            }.runTaskTimer(Main.pl, 20, 20);
        }
    }

    public void toggleAbilty(Player p) {
        String prefix = m.getMessage("prefix");
        if (toggled.contains(p.getUniqueId())) {
            toggled.remove(p.getUniqueId());
            p.sendMessage(prefix + " " + m.getMessage("abilityToggledOn"));
        } else {
            toggled.add(p.getUniqueId());
            p.sendMessage(prefix + " " + m.getMessage("abilityToggledOff"));
        }
    }

    public boolean getViewMorph(Player p) {
        File userFile = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);
        return fileConfig.getBoolean("viewDisguise");
    }

    public void setViewMorph(Player p, boolean ownView) {
        File userFile = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);

        fileConfig.set("viewDisguise", ownView);
        try {
            fileConfig.save(userFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String prefix = m.getMessage("prefix");
        if (ownView)
            p.sendMessage(prefix + " " + m.getMessage("changeViewSuccess").replace("%status%", "now"));
        else
            p.sendMessage(prefix + " " + m.getMessage("changeViewSuccess").replace("%status%", "no longer"));

        if (Main.getMorphManager().getUsing(p) != null) {
            boolean baby = Main.using.get(p.getUniqueId()).split(" ")[0].equalsIgnoreCase("baby");
            viewMorphBuffer.add(p.getUniqueId());
            Morph type = Main.getMorphManager().getMorphType(Main.getMorphManager().getUsing(p));
            morphPlayer(p, type, true, baby);
        }
    }

    public void setSoundsEnabled(Player p, boolean status) {
        File userFile = new File(Main.pl.getDataFolder() + "/UserData/" + p.getUniqueId() + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(userFile);

        fileConfig.set("sounds", status);
        try {
            fileConfig.save(userFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String prefix = m.getMessage("prefix");
        if (status) {
            p.sendMessage(prefix + " " + m.getMessage("changeSoundVolSuccess").replace("%statusColor%", ChatColor.GREEN + "enabled"));
            soundDisabled.remove(p.getUniqueId());
        } else {
            p.sendMessage(prefix + " " + m.getMessage("changeSoundVolSuccess").replace("%statusColor%", ChatColor.RED + "disabled"));
            soundDisabled.add(p.getUniqueId());
        }
    }

    public ItemStack getMorphItem() {
        List<String> lore = new ArrayList<>();
        Material mat = Material.matchMaterial(Main.pl.getConfig().getString("morphItem.type"));
        int data = Main.pl.getConfig().getInt("morphItem.data");
        String name = ChatColor.translateAlternateColorCodes('&', Main.pl.getConfig().getString("morphItem.name"));

        Main.pl.getConfig().getStringList("morphItem.lore").forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        ItemStack item = new ItemStack(mat, 1, (byte)data);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    public String getUsing(Player p) {
        if (!Main.using.containsKey(p.getUniqueId()))
            return null;
        String using;
        String[] data = Main.using.get(p.getUniqueId()).split(" ");
        if (data.length > 1)
            using = data[1];
        else
            using = data[0];
        return using;
    }

    public Morph getUsingMorph(Player p) {
        if (!Main.using.containsKey(p.getUniqueId()))
            return null;
        String using;
        String[] data = Main.using.get(p.getUniqueId()).split(" ");
        if (data.length > 1)
            using = data[1];
        else
            using = data[0];

        return Main.getMorphManager().getMorphs().get(using);
    }

    public boolean isBaby(Player p) {
        if (!Main.using.containsKey(p.getUniqueId()))
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
        Morph morph = getUsingMorph(p);
        if (morph == null) {
            return Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        }
        return morph.getSound();
    }

}
