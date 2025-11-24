package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.kreidev.cbmnfieldlab.network.FieldLabNetworkManager;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;


@SuppressWarnings("unused")
public class PokemonFieldLab {
    public static final String MOD_ID = "cbmnfieldlab";
    public static final String FIELD_LAB_NAME = "pokemon_field_lab";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MOD_ID, Registries.MENU);

    public static final RegistrySupplier<FieldLabBlock> FIELD_LAB_BLOCK = BLOCKS.register(FIELD_LAB_NAME,
            () -> new FieldLabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .sound(SoundType.METAL)
                    .pushReaction(PushReaction.BLOCK)
                    .strength(2F)
                    .noOcclusion()
                    .lightLevel(state -> (state.getValue(FieldLabBlock.OPEN) && state.getValue(FieldLabBlock.HALF) == DoubleBlockHalf.UPPER) ? 10 : 0)
            )
    );
    public static final RegistrySupplier<BlockEntityType<FieldLabBlockEntity>> FIELD_LAB_BLOCK_ENTITY = BLOCK_ENTITIES
            .register(FIELD_LAB_NAME, ()-> BlockEntityType.Builder.of(FieldLabBlockEntity::new, FIELD_LAB_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockItem> FIELD_LAB_BLOCK_ITEM = ITEMS
            .register(FIELD_LAB_NAME, () -> new BlockItem(FIELD_LAB_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<MenuType<FieldLabMenu>> FIELD_LAB_MENU = MENUS
            .register(FIELD_LAB_NAME, () -> MenuRegistry.ofExtended(FieldLabMenu::new));


    public static void init() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();
        ITEMS.register();
        MENUS.register();

        LifecycleEvent.SERVER_STARTING.register(PokemonFieldLab::onServerStarting);

        QuestTypes.init();
        FieldLabNetworkManager.registerPackets();
    }

    public static void initClient() {

    }

    public static void onServerStarting(MinecraftServer instance) {
        // Init data loader
        ServerLevel level = instance.getLevel(Level.OVERWORLD);
        if(level != null && !level.isClientSide()) {
            QuestManager.INSTANCE = level.getDataStorage().computeIfAbsent(
                    new SavedData.Factory<>(QuestManager::new, QuestManager::new, null), "cobblemon_field_lab_data"
            );
        }
    }

    public static ResourceLocation resLoc(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, args));
    }
}
