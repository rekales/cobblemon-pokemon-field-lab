package com.kreidev.cbmnfieldlab.network;

import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public record RefreshScreenPacket(PlayerQuestContainer container) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RefreshScreenPacket> TYPE =
            new CustomPacketPayload.Type<>(resLoc("refresh_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RefreshScreenPacket> STREAM_CODEC =
            StreamCodec.composite(PlayerQuestContainer.STREAM_CODEC, RefreshScreenPacket::container, RefreshScreenPacket::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
