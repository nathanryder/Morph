package me.bumblebeee_.morph.versions;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Actionbar_1_18_R1 implements Actionbar {

    @Override
    public void sendActionbar(Player p, String msg) {
        try {
            p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
        } catch (NoSuchMethodError e) {
            //Not spigot, ignore and don't show action bar
        }
    }
}
