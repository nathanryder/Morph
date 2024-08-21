package me.bumblebeee_.morph;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

import static java.util.Map.entry;

public enum VersionedPotionEffectType {

    //ORDER MATTERS! Highest at the bottom, format in 20.5 for 1.20.5 and 13.0 for 1.13.0
    INSTANT_DAMAGE(Map.ofEntries(
            entry(13.0, "DAMAGE_RESISTANCE"),
            entry(20.5, "INSTANT_DAMAGE")
    )),
    SLOWNESS(Map.ofEntries(
            entry(13.0, "SLOW"),
            entry(20.5, "SLOWNESS")
    )),
    JUMP_BOOST(Map.ofEntries(
            entry(13.0, "JUMP"),
            entry(20.5, "JUMP_BOOST")
    ));

    private PotionEffectType potionEffectType;

    VersionedPotionEffectType(Map<Double, String> versionMap) {
        double currVersion;
        try {
            String versionStr = Bukkit.getBukkitVersion().split("-")[0];
            versionStr = versionStr.substring(2);

            currVersion = Double.parseDouble(versionStr);
        } catch (ArrayIndexOutOfBoundsException e) {
            Bukkit.getServer().getLogger().severe("Failed to find a valid server version! Report this to the developer immediately");
            currVersion = 1.2;
        }

        for (Double version : versionMap.keySet()) {
            if (currVersion < version) {
                continue;
            }

            potionEffectType = PotionEffectType.getByName(versionMap.get(version));
        }
    }

    public PotionEffectType get() {
        return potionEffectType;
    }


}
