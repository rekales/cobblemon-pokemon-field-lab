package com.kreidev.cbmnfieldlab;


import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import org.slf4j.Logger;

@SuppressWarnings("unused")
public class PokemonFieldLab {
    public static final String MOD_ID = "cbmnfieldlab";
    public static final String FIELD_LAB_NAME = "pokemon_field_lab";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU);

    public static final RegistrySupplier<FieldLabBlock> FIELD_LAB_BLOCK = BLOCKS.register(FIELD_LAB_NAME, FieldLabBlock::new);
    public static final RegistrySupplier<BlockItem> FIELD_LAB_BLOCK_ITEM = ITEMS
            .register(FIELD_LAB_NAME, () -> new BlockItem(FIELD_LAB_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<MenuType<FieldLabMenu>> FIELD_LAB_MENU = MENUS
            .register(FIELD_LAB_NAME, () -> MenuRegistry.ofExtended(FieldLabMenu::new));


    public static void init() {
        BLOCKS.register();
        ITEMS.register();
        MENUS.register();
        registerToCreativeTab();
    }

    private static void registerToCreativeTab() {
//        CreativeTabRegistry.append(CreativeModeTabs.INGREDIENTS, PFL_BLOCK_ITEM.get());
    }

    public static ResourceLocation resLoc(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, args));
    }
}
