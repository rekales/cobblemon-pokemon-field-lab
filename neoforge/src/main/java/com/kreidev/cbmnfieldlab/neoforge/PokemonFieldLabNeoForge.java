package com.kreidev.cbmnfieldlab.neoforge;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.MOD_ID;

@Mod(MOD_ID)
public class PokemonFieldLabNeoForge {

    public PokemonFieldLabNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        PokemonFieldLab.init();
    }
}
