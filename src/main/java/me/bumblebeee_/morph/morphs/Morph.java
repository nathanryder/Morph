package me.bumblebeee_.morph.morphs;

import lombok.Getter;
import me.bumblebeee_.morph.Config;
import me.bumblebeee_.morph.Inventorys;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.Messages;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class Morph {

    private @Getter String internalName;
    private @Getter String morphName;
    private @Getter boolean enabled;
    private @Getter int health;
    private @Getter int requiredKills;
    private @Getter int morphTime;
    private @Getter int morphCooldown;
    private @Getter Sound sound;
    private @Getter DisguiseType disguiseType;
    private @Getter List<PotionEffect> effects = new ArrayList<>();

    //Optional - use incase morph needs any initial setups
    public void initMorph(Player p) {
    }

    public String toFriendly() {
        StringBuilder friendly = new StringBuilder();
        String[] data = getMorphName().replace("_", " ").split("");

        for (int i = 0; i < data.length; i++) {
            if (i == 0) {
                friendly.append(data[i].toUpperCase());
                continue;
            }
            if (data[i-1].equals(" ")) {
                friendly.append(data[i].toUpperCase());
                continue;
            }

            friendly.append(data[i]);
        }

        return friendly.toString();
    }

    public int getRequiredKills() {
        return Config.MOB_CONFIG.getRequiredKills(morphName);
    }

    public boolean isMorphedAsThis(Player p) {
        if (!DisguiseAPI.isDisguised(p))
            return false;
        if (!Main.using.containsKey(p.getUniqueId()))
            return false;
        if (!Main.getMorphManager().getUsing(p).equalsIgnoreCase(getMorphName()))
            return false;

        return true;
    }

    public Morph abilityInfo(String... message) {
        Messages.listMsgs.put("abilityInfo." + getMorphName(), new ArrayList<>(Arrays.asList(message)));
        return this;
    }

    public Morph runnable(BukkitRunnable runnable, int interval) {
        runnable.runTaskTimer(Main.pl, 0, interval);
        return this;
    }

    public Morph potionEffect(PotionEffect effect) {
        effects.add(effect);
        return this;
    }

    public Morph internalName(String internalName) {
        this.internalName = internalName;
        return this;
    }

    public Morph morphName(String morphName) {
        this.morphName = morphName;
        return this;
    }

    public Morph enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Morph health(int health) {
        this.health = health;
        return this;
    }

    public Morph requiredKills(int requiredKills) {
        this.requiredKills = requiredKills;
        return this;
    }

    public Morph morphTime(int morphTime) {
        this.morphTime = morphTime;
        return this;
    }

    public Morph morphCooldown(int morphCooldown) {
        this.morphCooldown = morphCooldown;
        return this;
    }

    public Morph headId(String headID) {
        Inventorys.addHead(getMorphName(), headID);
        return this;
    }

    public Morph sound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public Morph disguiseType(DisguiseType type) {
        this.disguiseType = type;
        return this;
    }
}
