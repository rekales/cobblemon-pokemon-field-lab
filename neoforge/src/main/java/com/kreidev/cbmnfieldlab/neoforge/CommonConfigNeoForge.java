package com.kreidev.cbmnfieldlab.neoforge;

import com.kreidev.cbmnfieldlab.CommonConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfigNeoForge {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue REROLL_TIME_SECONDS = BUILDER
            .comment("Time before a quest reroll becomes available (seconds)")
            .defineInRange("rerollTimeSeconds", 300, 1, 86400);

    // Quest type toggles
    private static final ModConfigSpec.BooleanValue ENABLE_ABILITY_QUEST = BUILDER
            .comment("Enable ability quests")
            .define("enableAbilityQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_SINGLE_TYPE_QUEST = BUILDER
            .comment("Enable single-type quests")
            .define("enableSingleTypeQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_DOUBLE_TYPE_QUEST = BUILDER
            .comment("Enable double-type quests")
            .define("enableDoubleTypeQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_NATURE_QUEST = BUILDER
            .comment("Enable nature quests")
            .define("enableNatureQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_MOVE_QUEST = BUILDER
            .comment("Enable move quests")
            .define("enableMoveQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_SIZE_QUEST = BUILDER
            .comment("Enable size quests")
            .define("enableSizeQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_BASE_STAT_QUEST = BUILDER
            .comment("Enable base stat total quests")
            .define("enableBaseStatQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_WEIGHT_QUEST = BUILDER
            .comment("Enable weight quests")
            .define("enableWeightQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_DEX_NAME_QUEST = BUILDER
            .comment("Enable pokedex name quests")
            .define("enableMoveQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_REGION_QUEST = BUILDER
            .comment("Enable region quests")
            .define("enableSizeQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_BIOME_QUEST = BUILDER
            .comment("Enable biome spawn quests")
            .define("enableBaseStatQuest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_EVO_STAGE_QUEST = BUILDER
            .comment("Enable evolution stage quests")
            .define("enableWeightQuest", true);

    // Size quest bounds
    private static final ModConfigSpec.DoubleValue SIZE_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum size factor for size quests")
            .defineInRange("sizeQuestLowerBound", 0.5D, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue SIZE_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum size factor for size quests")
            .defineInRange("sizeQuestUpperBound", 2.0D, 0.0D, 100.0D);

    // Weight quest bounds
    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum weight factor for weight quests")
            .defineInRange("weightQuestLowerBound", 0.5D, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum weight factor for weight quests")
            .defineInRange("weightQuestUpperBound", 2.0D, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_MIN_GAP = BUILDER
            .comment("Minimum gap between lower and upper weight bounds")
            .defineInRange("weightQuestMinGap", 0.5D, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_MAX_GAP = BUILDER
            .comment("Maximum gap between lower and upper weight bounds")
            .defineInRange("weightQuestMaxGap", 1.0D, 0.0D, 100.0D);

    // Base stat quest bounds
    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum total base stat value for base stat quests")
            .defineInRange("baseStatQuestLowerBound", 200, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum total base stat value for base stat quests")
            .defineInRange("baseStatQuestUpperBound", 400, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_MIN_GAP = BUILDER
            .comment("Minimum difference between lower and upper base stat bounds")
            .defineInRange("baseStatQuestMinGap", 30, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_MAX_GAP = BUILDER
            .comment("Maximum difference between lower and upper base stat bounds")
            .defineInRange("baseStatQuestMaxGap", 80, 0, 2000);


    static final ModConfigSpec SPEC = BUILDER.build();

    private static void loadValues() {
        CommonConfig.rerollTimeSeconds = REROLL_TIME_SECONDS.get();

        CommonConfig.enableAbilityQuest = ENABLE_ABILITY_QUEST.get();
        CommonConfig.enableSingleTypeQuest = ENABLE_SINGLE_TYPE_QUEST.get();
        CommonConfig.enableDoubleTypeQuest = ENABLE_DOUBLE_TYPE_QUEST.get();
        CommonConfig.enableNatureQuest = ENABLE_NATURE_QUEST.get();
        CommonConfig.enableMoveQuest = ENABLE_MOVE_QUEST.get();
        CommonConfig.enableSizeQuest = ENABLE_SIZE_QUEST.get();
        CommonConfig.enableBaseStatQuest = ENABLE_BASE_STAT_QUEST.get();
        CommonConfig.enableWeightQuest = ENABLE_WEIGHT_QUEST.get();
        CommonConfig.enableDexNameQuest = ENABLE_DEX_NAME_QUEST.get();
        CommonConfig.enableRegionQuest = ENABLE_REGION_QUEST.get();
        CommonConfig.enableBiomeQuest = ENABLE_BIOME_QUEST.get();
        CommonConfig.enableEvoQuest = ENABLE_EVO_STAGE_QUEST.get();


        CommonConfig.sizeQuestLowerBound = SIZE_QUEST_LOWER_BOUND.get().floatValue();
        CommonConfig.sizeQuestUpperBound = SIZE_QUEST_UPPER_BOUND.get().floatValue();

        CommonConfig.weightQuestLowerBound = WEIGHT_QUEST_LOWER_BOUND.get().floatValue();
        CommonConfig.weightQuestUpperBound = WEIGHT_QUEST_UPPER_BOUND.get().floatValue();
        CommonConfig.weightQuestMinGap = WEIGHT_QUEST_MIN_GAP.get().floatValue();
        CommonConfig.weightQuestMaxGap = WEIGHT_QUEST_MAX_GAP.get().floatValue();

        CommonConfig.baseStatQuestLowerBound = BASE_STAT_QUEST_LOWER_BOUND.get();
        CommonConfig.baseStatQuestUpperBound = BASE_STAT_QUEST_UPPER_BOUND.get();
        CommonConfig.baseStatQuestMinGap = BASE_STAT_QUEST_MIN_GAP.get();
        CommonConfig.baseStatQuestMaxGap = BASE_STAT_QUEST_MAX_GAP.get();
    }

    @SuppressWarnings("unused")
    static void onLoad(final ModConfigEvent.Loading event) {
        loadValues();
    }

    @SuppressWarnings("unused")
    static void onReload(final ModConfigEvent.Loading event) {
        loadValues();
    }
}
