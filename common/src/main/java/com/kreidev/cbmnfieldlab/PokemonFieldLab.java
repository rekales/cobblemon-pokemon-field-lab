package com.kreidev.cbmnfieldlab;


import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class PokemonFieldLab {
    public static final String MOD_ID = "cbmnfieldlab";

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {

    }

    public static ResourceLocation resLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}
