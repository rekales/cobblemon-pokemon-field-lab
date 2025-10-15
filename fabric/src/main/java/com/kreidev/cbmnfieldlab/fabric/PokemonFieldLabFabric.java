package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.fabricmc.api.ModInitializer;

public class PokemonFieldLabFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PokemonFieldLab.init();
    }
}
