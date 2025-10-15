package com.kreidev.templatemod;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class TemplateMod {
    public static final String MOD_ID = "templatemod";

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
    }

    public static ResourceLocation resLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
