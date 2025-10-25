package com.kreidev.cbmnfieldlab.network;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class FieldLabNetworkManager {

    public static void registerPackets() {
        // TODO: replace with non-deprecated methods
        // Maybe integrate with cobblemon's network manager
        // https://deepwiki.com/search/does-this-mod-use-custom-packe_ebf73746-67cb-48d3-a80e-a0a74f85c871?mode=fast

        NetworkManager.registerReceiver(
                NetworkManager.Side.C2S,
                RerollPacket.TYPE,
                RerollPacket.STREAM_CODEC,
                FieldLabNetworkManager::rerollQuest
        );

        NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                RefreshScreenPacket.TYPE,
                RefreshScreenPacket.STREAM_CODEC,
                FieldLabNetworkManager::refreshScreen
        );
    }

    public static void submitQuest(RegistryFriendlyByteBuf buf, NetworkManager.PacketContext context) {

    }

    public static void rerollQuest(RerollPacket packet, NetworkManager.PacketContext context) {
        if (!(context.getPlayer() instanceof ServerPlayer player)) return;
        int index = packet.index();
        QuestManager.rerollQuest(player, index);
    }

    public static void refreshScreen(RefreshScreenPacket packet, NetworkManager.PacketContext context) {
        PokemonFieldLab.LOGGER.info("refresh");
        // TODO: refresh packet sending quest container data, need to do codec first
    }
}
