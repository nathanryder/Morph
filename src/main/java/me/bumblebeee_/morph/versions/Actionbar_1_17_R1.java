package me.bumblebeee_.morph.versions;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import net.minecraft.network.chat.ChatMessageType;

import org.bukkit.entity.Player;

public class Actionbar_1_17_R1 implements Actionbar {

    @Override
    public void sendActionbar(Player p, String msg) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat bar2 = new PacketPlayOutChat(icbc, ChatMessageType.c, p.getUniqueId());
        ((CraftPlayer) p).getHandle().b.sendPacket(bar2);
    }
}
