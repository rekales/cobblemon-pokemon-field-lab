package com.kreidev.cbmnpokemonfieldlab.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import com.kreidev.cbmnpokemonfieldlab.PokemonFieldLab;

public final class PokemonFieldLabFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        PokemonFieldLab.init();
        loadConfigs();
    }

    public static void loadConfigs() {
        MidnightConfig.init(PokemonFieldLab.MOD_ID, CommonConfigFabric.class);
    }
}
