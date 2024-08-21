package me.bumblebeee_.morph.events;

import me.bumblebeee_.morph.Main;
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
    public static ArrayList<UUID> blow = new ArrayList<>();

    @EventHandler
    public void onUndisguise(UndisguiseEvent e) {
        //getDisguised() has been replaced from getEntity() - may break older versions
        if (LibsDisguises.getInstance().getBuildNumber() >= 934) {
            if (!(e.getDisguised() instanceof Player))
                return;

            if (!blow.contains(e.getDisguised().getUniqueId())) {
                return;
            }

            if (Main.undisguiseBuffer.contains(e.getDisguised().getUniqueId())) {
                Main.undisguiseBuffer.remove(e.getDisguised().getUniqueId());
                return;
            }

            Player p = (Player) e.getDisguised();
            if (Main.getMorphManager().viewMorphBuffer.contains(p.getUniqueId())) {
                Main.getMorphManager().viewMorphBuffer.remove(p.getUniqueId());
                return;
            }

            if (!Main.using.containsKey(p.getUniqueId()))
                return;

            morph.unmorphPlayer(p, false, false);
            blow.remove(e.getDisguised().getUniqueId());
        }
    }

}
