package me.bumblebeee_.morph.managers;

import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.versions.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ManaManager {

    public static Actionbar ab;
    public static String version;
    boolean enabled = false;

    public static HashMap<UUID, Double> mana = new HashMap<>();

    public void setup() {
        if (!setupActionbar()) {
            Main.pl.getLogger().info("Failed to find actionbar support for this version..");
            Main.pl.getLogger().info("Morph power cannot be used. Please use a supported minecraft version if you wish to use it.");
        } else {
            enabled = true;
        }
    }

    public boolean setupActionbar() {
        try {
            String[] verData = Bukkit.getBukkitVersion().split("-");
            version = "v" + verData[0].substring(0, 4).replace(".", "_");
            version += "_R" + verData[1].split("\\.")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            Main.pl.getLogger().warning("Failed to find server version");
            return false;
        }


        if (Main.pl.getConfig().getBoolean("debug")) {
            Main.pl.getLogger().info("FOUND VERSION: " + version);
        }
        if (version.equals("v1_11_R1"))
            ab = new Actionbar_1_11_R1();
        else if (version.equalsIgnoreCase("v1_9_R1"))
            ab = new Actionbar_1_9_R1();
        else if (version.equalsIgnoreCase("v1_9_R2"))
            ab = new Actionbar_1_9_R2();
        else if (version.equalsIgnoreCase("v1_8_R3"))
            ab = new Actionbar_1_8_R1();
        else if (version.equalsIgnoreCase("v1_12_R1"))
            ab = new Actionbar_1_12_R1();
        else if (version.equalsIgnoreCase("v1_13_R1"))
            ab = new Actionbar_1_13_R1();
        else if (version.equalsIgnoreCase("v1_13_R2"))
            ab = new Actionbar_1_13_R2();
        else if (version.equalsIgnoreCase("v1_14_R1"))
            ab = new Actionbar_1_14_R1();
        else if (version.equalsIgnoreCase("v1_15_R1"))
            ab = new Actionbar_1_15_R1();
        else if (version.equalsIgnoreCase("v1_16_R1"))
            ab = new Actionbar_1_16_R1();
        else if (version.equalsIgnoreCase("v1_16_R2"))
            ab = new Actionbar_1_16_R2();
        else if (version.equalsIgnoreCase("v1_16_R3"))
            ab = new Actionbar_1_16_R3();
        else if (version.equalsIgnoreCase("v1_17_R1"))
            ab = new Actionbar_1_17_R1();
        else
            ab = new Actionbar_1_18_R1();

        return ab != null;
    }

    public double getMana(Player p) {
        if (!getManaPlayers().containsKey(p.getUniqueId()))
            return -1;
        return getManaPlayers().get(p.getUniqueId());
    }


    public void takeMana(Player p, double amount) {
        if (!getManaPlayers().containsKey(p.getUniqueId()))
            return;

        double ca = getManaPlayers().get(p.getUniqueId());
        getManaPlayers().remove(p.getUniqueId());
        getManaPlayers().put(p.getUniqueId(), ca-amount);
    }

    public void addMana(Player p, double amount) {
        if (!getManaPlayers().containsKey(p.getUniqueId()))
            return;

        double ca = getManaPlayers().get(p.getUniqueId());
        getManaPlayers().remove(p.getUniqueId());
        getManaPlayers().put(p.getUniqueId(), ca+amount);
    }

    public HashMap<UUID, Double> getManaPlayers() { return mana; }

}
