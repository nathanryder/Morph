package me.bumblebeee_.morph.versions;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Actionbar_1_16_R3 implements Actionbar {

    @Override
    public void sendActionbar(Player p, String msg) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat bar2 = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO, p.getUniqueId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar2);
    }

}
