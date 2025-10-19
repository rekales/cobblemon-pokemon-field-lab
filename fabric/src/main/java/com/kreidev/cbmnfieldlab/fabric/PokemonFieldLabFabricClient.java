package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class PokemonFieldLabFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(PokemonFieldLab.FIELD_LAB_MENU.get(), FieldLabScreen::new);
    }
}