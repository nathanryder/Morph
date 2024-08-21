package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Morph;
import me.bumblebeee_.morph.MorphManager;
import me.libraryaddict.disguise.LibsDisguises;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerUndisguise implements Listener {

    MorphManager morph = new MorphManager();
    static ArrayList<UUID> blow = new ArrayList<>();

    @EventHandler
    public void onUndisguise(UndisguiseEvent e) {
        //getDisguised() has been replaced from getEntity() - may break older versions
        if (LibsDisguises.getInstance().getBuildNumber() >= 934) {
            if (!(e.getDisguised() instanceof Player))
                return;

            if (!blow.contains(e.getDisguised().getUniqueId())) {
                return;
            }

            if (Morph.undisguiseBuffer.contains(e.getDisguised().getUniqueId())) {
                Morph.undisguiseBuffer.remove(e.getDisguised().getUniqueId());
                return;
            }

            Player p = (Player) e.getDisguised();
            if (MorphManager.viewMorphBuffer.contains(p.getUniqueId())) {
                MorphManager.viewMorphBuffer.remove(p.getUniqueId());
                return;
            }

            if (!Morph.using.containsKey(p.getUniqueId()))
                return;

            morph.unmorphPlayer(p, false, false);
            blow.remove(e.getDisguised().getUniqueId());
        }
    }

}
