package com.kreidev.cbmnfieldlab.neoforge;

import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.quest.neoforge.SavedQuestData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.MOD_ID;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

@Mod(MOD_ID)
public class PokemonFieldLabNeoForge {

    public PokemonFieldLabNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        PokemonFieldLab.init();
        modEventBus.addListener(PokemonFieldLabNeoForge::onRegisterMenuScreens);
        NeoForge.EVENT_BUS.addListener(PokemonFieldLabNeoForge::onServerStarted);
    }

    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(PokemonFieldLab.FIELD_LAB_MENU.get(), FieldLabScreen::new);
    }

    public static void onServerStarted(ServerStartedEvent event) {
        //NOTE: copied from IE, hardcoding DimensionType.OVERWORLD does seem hacky/broken
        ServerLevel level = event.getServer().getLevel(Level.OVERWORLD);
        if(level != null && !level.isClientSide) {
            SavedQuestData.INSTANCE = level.getDataStorage().computeIfAbsent(
                    new SavedData.Factory<>(SavedQuestData::new, SavedQuestData::new), "cobblemon_field_lab_data"
            );
        }
    }
}
