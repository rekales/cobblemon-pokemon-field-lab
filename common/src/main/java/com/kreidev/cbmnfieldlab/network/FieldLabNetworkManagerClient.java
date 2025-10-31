package com.kreidev.cbmnfieldlab.network;

import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;

public class FieldLabNetworkManagerClient {

    public static void registerPacketsClient() {
        NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                RefreshScreenPacket.TYPE,
                RefreshScreenPacket.STREAM_CODEC,
                FieldLabNetworkManagerClient::refreshScreen
        );
    }

    public static void refreshScreen(RefreshScreenPacket packet, NetworkManager.PacketContext context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof FieldLabScreen screen) {
            screen.updateQuests(packet.container());
        }
    }
}
