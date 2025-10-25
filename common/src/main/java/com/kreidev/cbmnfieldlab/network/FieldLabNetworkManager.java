package com.kreidev.cbmnfieldlab.network;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import com.mojang.serialization.Codec;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

public class FieldLabNetworkManager {

    public static void registerPackets() {
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
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof FieldLabScreen screen) {
            screen.updateQuests(packet.container());
        }
    }

    public static <B extends FriendlyByteBuf, T> StreamCodec<B, T> fromCodec(Codec<T> codec) {
        return new StreamCodec<>() {
            @Override
            public T decode(B buf) {
                CompoundTag tag = buf.readNbt();
                return codec.parse(NbtOps.INSTANCE, tag)
                        .resultOrPartial(error -> LOGGER.error("Decode error: " + error))
                        .orElseThrow();
            }

            @Override
            public void encode(B buf, T value) {
                codec.encodeStart(NbtOps.INSTANCE, value)
                        .resultOrPartial(error -> LOGGER.error("Encode error: " + error))
                        .ifPresent(buf::writeNbt);
            }
        };
    }
}
