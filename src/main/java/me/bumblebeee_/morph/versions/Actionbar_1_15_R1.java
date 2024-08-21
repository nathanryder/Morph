package me.bumblebeee_.morph.versions;

import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Actionbar_1_15_R1 implements Actionbar {

    @Override
    public void sendActionbar(Player p, String msg) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat bar2 = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar2);
    }

}
