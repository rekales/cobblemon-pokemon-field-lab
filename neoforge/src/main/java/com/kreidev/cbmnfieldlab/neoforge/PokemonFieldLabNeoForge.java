package com.kreidev.cbmnfieldlab.neoforge;

import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.MOD_ID;

@Mod(MOD_ID)
public class PokemonFieldLabNeoForge {

    public PokemonFieldLabNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        PokemonFieldLab.init();
        modEventBus.addListener(PokemonFieldLabNeoForge::onRegisterMenuScreens);
    }

    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(PokemonFieldLab.FIELD_LAB_MENU.get(), FieldLabScreen::new);
    }
}
