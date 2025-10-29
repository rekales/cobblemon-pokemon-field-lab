package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.quest.type.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Function;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class QuestTypes {

    public static final QuestType<AbilityQuest> ABILITY = register("ability", AbilityQuest.CODEC, AbilityQuest::new);
    public static final QuestType<TypeQuest> SINGLE_TYPE = register("1_type", TypeQuest.CODEC, TypeQuest::new);
    public static final QuestType<DoubleTypeQuest> DOUBLE_TYPE = register("2_types", DoubleTypeQuest.CODEC, DoubleTypeQuest::new);
    public static final QuestType<NatureQuest> NATURE = register("nature", NatureQuest.CODEC, NatureQuest::new);
    public static final QuestType<MoveQuest> MOVE = register("move", MoveQuest.CODEC, MoveQuest::new);
    public static final QuestType<SizeQuest> SIZE = register("size", SizeQuest.CODEC, SizeQuest::new);
    public static final QuestType<StatQuest> BASE_STAT = register("base_stats_total", StatQuest.CODEC, StatQuest::new);
    public static final QuestType<WeightQuest> WEIGHT = register("weight", WeightQuest.CODEC, WeightQuest::new);
    public static final QuestType<DexNameQuest> DEX_NAME = register("pokedex_name", DexNameQuest.CODEC, DexNameQuest::new);
    public static final QuestType<RegionQuest> REGION = register("region", RegionQuest.CODEC, RegionQuest::new);
    public static final QuestType<BiomeQuest> BIOME = register("biome", BiomeQuest.CODEC, BiomeQuest::new);


    public static <T extends Quest> QuestType<T> register(String id, MapCodec<T> codec, Function<ServerLevel, Quest> randomQuestFactory) {
        return Registry.register(QuestType.REGISTRY, resLoc(id), new QuestType<>(id, codec, randomQuestFactory));
    }

    public static void init() {}
}
