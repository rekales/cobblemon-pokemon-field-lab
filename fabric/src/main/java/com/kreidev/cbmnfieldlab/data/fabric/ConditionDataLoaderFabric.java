package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.ConditionDataLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class ConditionDataLoaderFabric extends ConditionDataLoader implements IdentifiableResourceReloadListener {

    public static final ConditionDataLoaderFabric INSTANCE = new ConditionDataLoaderFabric();

    @Override
    public ResourceLocation getFabricId() {
        return resLoc("quest_condition");
    }
}