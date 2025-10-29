package com.kreidev.cbmnfieldlab;


import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition;
import com.cobblemon.mod.common.api.pokedex.Dexes;
import com.cobblemon.mod.common.api.pokedex.def.PokedexDef;
import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.CobblemonWorldSpawnerManager;
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnPool;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.kreidev.cbmnfieldlab.network.FieldLabNetworkManager;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.kreidev.cbmnfieldlab.quest.type.BiomeQuest;
import com.mojang.logging.LogUtils;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;

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

        LifecycleEvent.SERVER_STARTING.register(PokemonFieldLab::onServerStarting);

        PlayerEvent.DROP_ITEM.register((player, itemEntity)->{
            if (player instanceof ServerPlayer serverPlayer) {
                Pokemon pokemon = PlayerExtensionsKt.party(serverPlayer).get(0);
                if (pokemon != null) {
                    Quest quest = new BiomeQuest(serverPlayer.serverLevel());
                    LOGGER.info(quest+"");
                    LOGGER.info(quest.isEligible(pokemon)+"");
                    LOGGER.info(pokemon.getSpecies()+"");
                }
//
//                if (pokemon != null) {
////                    CobblemonWorldSpawnerManager.INSTANCE.
//                    SpawnPool spawnPool = CobblemonSpawnPools.WORLD_SPAWN_POOL;
//
//                    ServerLevel level = serverPlayer.serverLevel();
//
//                    Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
////                    Biome biome = registry.get(Biomes.JUNGLE);
//                    Biome biome = registry.getRandom(level.getRandom()).orElseThrow().value();
//
////                    LOGGER.info(biome.toString());
//
//                    Set<String> species = CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails().stream()
//                            .filter(spawnDetail-> {
//                                for (SpawningCondition<?> cond : spawnDetail.getConditions()) {
//                                    if (cond.getBiomes() == null) continue;
//                                    for (RegistryLikeCondition<Biome> c : cond.getBiomes()) {
//                                        if (c.fits(biome, registry)) return true;
//                                    }
//                                }
//                                return false;
//                            }).map(spawnDetail -> {
//                                String specie = ((PokemonSpawnDetail) spawnDetail).getPokemon().getSpecies();
//                                if (specie == null) return null;
//                                return specie.toLowerCase();
//                            }).filter(Objects::nonNull)
//                            .collect(Collectors.toSet());
//
////                    LOGGER.info(species.toString());
//                    LOGGER.info(species.size()+"");
//                    LOGGER.info(PokemonSpecies.INSTANCE.count()+"");
//
////                    species.stream().peek(LOGGER::info);
//                }
            }

            return EventResult.pass();
        });

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

    private static void registerToCreativeTab() {
//        CreativeTabRegistry.append(CreativeModeTabs.INGREDIENTS, PFL_BLOCK_ITEM.get());
    }

    public static ResourceLocation resLoc(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, String.format(path, args));
    }
}
