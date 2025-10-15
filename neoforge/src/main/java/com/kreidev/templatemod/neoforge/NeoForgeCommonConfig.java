package com.kreidev.templatemod.neoforge;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeCommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static final ModConfigSpec SPEC = BUILDER.build();

    static void onLoad(final ModConfigEvent.Loading event) {
    }

    static void onReload(final ModConfigEvent.Reloading event) {
    }
}
