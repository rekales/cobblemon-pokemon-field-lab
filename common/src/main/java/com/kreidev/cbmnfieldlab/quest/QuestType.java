package com.kreidev.cbmnfieldlab.quest;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public record QuestType<T extends Quest>(MapCodec<T> codec) {

    public static final Registry<QuestType<?>> REGISTRY = new MappedRegistry<>(
            ResourceKey.createRegistryKey(resLoc("quest_types")), Lifecycle.stable()
    );
}
