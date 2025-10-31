package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.RewardDataLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class RewardDataLoaderFabric extends RewardDataLoader implements IdentifiableResourceReloadListener {

    public static final RewardDataLoaderFabric INSTANCE = new RewardDataLoaderFabric();

    @Override
    public ResourceLocation getFabricId() {
        return resLoc("quest_reward");
    }
}