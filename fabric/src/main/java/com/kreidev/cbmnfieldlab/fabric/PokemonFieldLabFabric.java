package com.kreidev.cbmnfieldlab.fabric;

import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.data.ConditionDataLoader;
import com.kreidev.cbmnfieldlab.data.RewardDataLoader;
import com.kreidev.cbmnfieldlab.data.fabric.ConditionDataLoaderFabric;
import com.kreidev.cbmnfieldlab.data.fabric.RewardDataLoaderFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class PokemonFieldLabFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PokemonFieldLab.init();

        registerCreativeTab();
        CommonConfigFabric.loadConfigs();
        loadData();
    }

    private static void loadData() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener((RewardDataLoaderFabric) RewardDataLoader.getInstance());
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener((ConditionDataLoaderFabric) ConditionDataLoader.getInstance());
    }

    private static void registerCreativeTab() {
        ItemGroupEvents.modifyEntriesEvent(CobblemonItemGroups.getBLOCKS_KEY())
                .register((itemGroup) -> itemGroup.accept(PokemonFieldLab.FIELD_LAB_BLOCK_ITEM.get()));
    }
}
