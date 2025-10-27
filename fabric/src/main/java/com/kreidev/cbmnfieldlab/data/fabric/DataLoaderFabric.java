package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.DataLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class DataLoaderFabric extends DataLoader implements IdentifiableResourceReloadListener {

    public static final DataLoaderFabric INSTANCE = new DataLoaderFabric();

    @Override
    public ResourceLocation getFabricId() {
        return resLoc("quest_reward");
    }
}