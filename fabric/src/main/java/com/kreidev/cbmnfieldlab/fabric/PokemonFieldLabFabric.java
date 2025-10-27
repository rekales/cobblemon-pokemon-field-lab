package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.data.DataLoader;
import com.kreidev.cbmnfieldlab.data.fabric.DataLoaderFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class PokemonFieldLabFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PokemonFieldLab.init();

        CommonConfigFabric.loadConfigs();
        loadData();
    }

    private static void loadData() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener((DataLoaderFabric) DataLoader.getInstance());
    }
}
