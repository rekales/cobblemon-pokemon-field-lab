package com.kreidev.cbmnfieldlab.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public record RerollPacket(int index) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RerollPacket> TYPE =
            new CustomPacketPayload.Type<>(resLoc("reroll_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RerollPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.INT, RerollPacket::index, RerollPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
