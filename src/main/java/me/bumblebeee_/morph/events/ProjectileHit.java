package me.bumblebeee_.morph.events;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ProjectileHit implements Listener {

    @EventHandler
    public void onProjHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow))
            return;

        Arrow a = (Arrow) e.getEntity();
        List<MetadataValue> v = a.getMetadata("morph");
        if (!(v.size() > 0))
            return;

        if (v.get(0).value().equals("yes:stray")) {
            if (e.getHitEntity() != null) {
                if (e.getHitEntity() instanceof Animals) {
                    Animals t = (Animals) e.getHitEntity();
                    for (PotionEffect p : t.getActivePotionEffects()) {
                        if (p.getType() == PotionEffectType.SLOW)
                            t.removePotionEffect(PotionEffectType.SLOW);
                    }
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
                } else if (e.getHitEntity() instanceof Monster) {
                    Monster t = (Monster) e.getHitEntity();
                    for (PotionEffect p : t.getActivePotionEffects()) {
                        if (p.getType() == PotionEffectType.SLOW)
                            t.removePotionEffect(PotionEffectType.SLOW);
                    }
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
                } else if (e.getHitEntity() instanceof Player) {
                    Player t = (Player) e.getHitEntity();
                    for (PotionEffect p : t.getActivePotionEffects()) {
                        if (p.getType() == PotionEffectType.SLOW)
                            t.removePotionEffect(PotionEffectType.SLOW);
                    }
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
                }
            }
            a.remove();
        } else if (v.get(0).value().equals("yes"))
            a.remove();
    }

}
