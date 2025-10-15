package com.kreidev.templatemod.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import com.kreidev.templatemod.TemplateMod;

public final class TemplateModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        TemplateMod.init();
        loadConfigs();
    }

    public static void loadConfigs() {
        MidnightConfig.init(TemplateMod.MOD_ID, CommonConfigFabric.class);
    }
}
