package com.kreidev.cbmnfieldlab.neoforge;

import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import com.kreidev.cbmnfieldlab.PokemonFieldLabClient;
import com.kreidev.cbmnfieldlab.data.ConditionDataLoader;
import com.kreidev.cbmnfieldlab.data.RewardDataLoader;
import com.kreidev.cbmnfieldlab.gui.FieldLabScreen;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.MOD_ID;

@Mod(MOD_ID)
public class PokemonFieldLabNeoForge {

    public PokemonFieldLabNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfigNeoForge.SPEC);
        PokemonFieldLab.init();
        PokemonFieldLabClient.initClient();
        NeoForge.EVENT_BUS.addListener(PokemonFieldLabNeoForge::onAddReloadListeners);
        modEventBus.addListener(PokemonFieldLabNeoForge::buildContents);
        modEventBus.addListener(PokemonFieldLabNeoForge::onRegisterMenuScreens);
        modEventBus.addListener(CommonConfigNeoForge::onLoad);
        modEventBus.addListener(CommonConfigNeoForge::onReload);
    }

    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(PokemonFieldLab.FIELD_LAB_MENU.get(), FieldLabScreen::new);
    }

    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(RewardDataLoader.getInstance());
        event.addListener(ConditionDataLoader.getInstance());
    }

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CobblemonItemGroups.getBLOCKS_KEY()) {
            event.accept(PokemonFieldLab.FIELD_LAB_BLOCK_ITEM.get());
        }
    }
}
