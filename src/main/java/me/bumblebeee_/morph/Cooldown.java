package me.bumblebeee_.morph;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {

    private static @Getter Map<UUID, Map<String, Integer>> cooldowns = new HashMap<>();

    public static void createCooldown(UUID uuid, String mob, int time) {
        Map<String, Integer> playerCd = new HashMap<>();
        if (cooldowns.containsKey(uuid))
            playerCd = cooldowns.get(uuid);

        playerCd.put(mob, time);
        cooldowns.put(uuid, playerCd);
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<String, Integer> cds = cooldowns.get(uuid);

                cds.put(mob, cds.get(mob) - 1);
                if (cds.get(mob) < 1) {
                    cds.remove(mob);
                    cooldowns.put(uuid, cds);
                    cancel();
                }

                cooldowns.put(uuid, cds);
            }
        }.runTaskTimer(Main.pl, 20, 20);
    }

    public static int getCooldown(UUID uuid, String mob) {
        if (!cooldowns.containsKey(uuid))
            return -1;

        Map<String, Integer> playerCd = cooldowns.get(uuid);
        if (!playerCd.containsKey(mob))
            return -1;

        return playerCd.get(mob);
    }

}
