package com.kreidev.cbmnfieldlab.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public record SubmitPacket(int partyPositionSlot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SubmitPacket> TYPE =
            new CustomPacketPayload.Type<>(resLoc("submit_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SubmitPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, SubmitPacket::partyPositionSlot,
                    SubmitPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
