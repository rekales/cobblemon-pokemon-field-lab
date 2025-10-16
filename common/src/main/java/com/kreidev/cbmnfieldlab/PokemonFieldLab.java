package com.kreidev.cbmnfieldlab;


import com.mojang.logging.LogUtils;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import org.slf4j.Logger;

public class PokemonFieldLab {
    public static final String MOD_ID = "cbmnfieldlab";

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<PFLBlock> PFL_BLOCK = BLOCKS.register("pokemon_field_lab", PFLBlock::new);
    public static final RegistrySupplier<BlockItem> PFL_BLOCK_ITEM = ITEMS
            .register("pokemon_field_lab", () -> new BlockItem(PFL_BLOCK.get(), new Item.Properties()));


    public static void init() {
        BLOCKS.register();
        ITEMS.register();
    }

    public static ResourceLocation resLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
