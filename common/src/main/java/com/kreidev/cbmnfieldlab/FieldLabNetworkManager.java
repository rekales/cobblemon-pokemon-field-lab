package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.quest.QuestManager;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class FieldLabNetworkManager {
    public static ResourceLocation REFRESH_SCREEN_ID = resLoc("refresh_screen");
    public static ResourceLocation SUBMIT_ID = resLoc("submit_quest");
    public static ResourceLocation REROLL_ID = resLoc("reroll_quest");

    private static final ResourceLocation CHANNEL = resLoc("network_channel");

    public static void registerPackets() {
        // TODO: replace with non-deprecated methods
        // Maybe integrate with cobblemon's network manager
        // https://deepwiki.com/search/does-this-mod-use-custom-packe_ebf73746-67cb-48d3-a80e-a0a74f85c871?mode=fast
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, REFRESH_SCREEN_ID, FieldLabNetworkManager::refreshScreen);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SUBMIT_ID, FieldLabNetworkManager::submitQuest);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, REROLL_ID, FieldLabNetworkManager::rerollQuest);
    }

    public static void refreshScreen(RegistryFriendlyByteBuf buf, NetworkManager.PacketContext context) {

    }

    public static void submitQuest(RegistryFriendlyByteBuf buf, NetworkManager.PacketContext context) {

    }

    public static void rerollQuest(RegistryFriendlyByteBuf buf, NetworkManager.PacketContext context) {
        if (!(context.getPlayer() instanceof ServerPlayer player)) return;
        int index = buf.readInt();
        QuestManager.rerollQuest(player, index);
    }



}
