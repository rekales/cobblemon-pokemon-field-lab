package com.kreidev.templatemod.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import com.kreidev.templatemod.TemplateMod;
import net.neoforged.fml.config.ModConfig;

import static com.kreidev.templatemod.TemplateMod.MOD_ID;

@Mod(MOD_ID)
public final class CraftingCooldownNeoForge {

    public CraftingCooldownNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeCommonConfig.SPEC);
        TemplateMod.init();
        modEventBus.addListener(NeoForgeCommonConfig::onLoad);
        modEventBus.addListener(NeoForgeCommonConfig::onReload);
    }
}
