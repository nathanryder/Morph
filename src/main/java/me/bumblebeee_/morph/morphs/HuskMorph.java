package me.bumblebeee_.morph.morphs;

import me.bumblebeee_.morph.managers.Config;
import me.bumblebeee_.morph.Main;
import me.bumblebeee_.morph.events.PlayerUndisguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HuskMorph extends Morph implements Listener {

    public HuskMorph() {
        this.morphName("husk")
                .internalName("craft" + getMorphName())
                .enabled(Config.MOB_CONFIG.isEnabled(getMorphName()))
                .disguiseType(DisguiseType.HUSK)
                .health(Config.MOB_CONFIG.getHealth(getMorphName()))
                .requiredKills(Config.MOB_CONFIG.getRequiredKills(getMorphName()))
                .morphTime(Config.MOB_CONFIG.getMorphTime(getMorphName()))
                .morphCooldown(Config.MOB_CONFIG.getMorphCooldown(getMorphName()))
                .sound(Sound.ENTITY_HUSK_AMBIENT)
                .headId("d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121")
                .abilityInfo("&5Passive: &eGives Hunger to whatever it attacks");

        Bukkit.getServer().getPluginManager().registerEvents(this, Main.pl);
    }

    @EventHandler
    public void onEntityDamageEntity(final EntityDamageByEntityEvent ev) {
        Entity e = ev.getDamager();
        if (!(e instanceof Player))
            return;
        final Player p = (Player) ev.getDamager();
        if (!Main.using.containsKey(p.getUniqueId()))
            return;
        if (!isMorphedAsThis(p))
            return;

        if (!PlayerUndisguise.blow.contains(p.getUniqueId()))
            PlayerUndisguise.blow.add(p.getUniqueId());

        Entity t = ev.getEntity();
        if (t instanceof LivingEntity) {
            LivingEntity ta = (LivingEntity) t;
            for (PotionEffect pe : ta.getActivePotionEffects()) {
                if (pe.getType() == PotionEffectType.HUNGER)
                    ta.removePotionEffect(PotionEffectType.HUNGER);
            }
            PotionEffect hunger = new PotionEffect(PotionEffectType.HUNGER, 20*7, 0, false ,false);
            ta.addPotionEffect(hunger);
        }

    }
}
