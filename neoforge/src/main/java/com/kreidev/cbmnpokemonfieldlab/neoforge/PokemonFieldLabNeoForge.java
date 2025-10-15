package com.kreidev.cbmnpokemonfieldlab.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import com.kreidev.cbmnpokemonfieldlab.PokemonFieldLab;
import net.neoforged.fml.config.ModConfig;

import static com.kreidev.cbmnpokemonfieldlab.PokemonFieldLab.MOD_ID;

@Mod(MOD_ID)
public final class PokemonFieldLabNeoForge {

    public PokemonFieldLabNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeCommonConfig.SPEC);
        PokemonFieldLab.init();
        modEventBus.addListener(NeoForgeCommonConfig::onLoad);
        modEventBus.addListener(NeoForgeCommonConfig::onReload);
    }
}
