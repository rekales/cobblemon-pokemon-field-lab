package com.kreidev.cbmnpokemonfieldlab;

import com.mojang.logging.LogUtils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

public final class PokemonFieldLab {
    public static final String MOD_ID = "cbmnpokemonfieldlab";

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> PFL_BLOCK =
            BLOCKS.register("pokemon_field_lab", PFLBlock::new);

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        BLOCKS.register();

    }

    public static ResourceLocation resLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
