package com.kreidev.cbmnfieldlab.neoforge;

import com.kreidev.cbmnfieldlab.CommonConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// NOTE: setting default values like this could probably cause issues
public class CommonConfigNeoForge {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue REROLL_TIME_SECONDS = BUILDER
            .comment("Time before a quest reroll becomes available (seconds)")
            .defineInRange("rerollTimeSeconds", CommonConfig.rerollTimeSeconds, 1, 86400);

    // Quest reward bounds
    private static final ModConfigSpec.DoubleValue INDIV_REWARD_VALUE_SCALE = BUILDER
            .comment("Scaling multiplier for individual quest reward value")
            .defineInRange("indivRewardValueScale", CommonConfig.indivRewardValueScale, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue MINOR_REWARD_VALUE = BUILDER
            .comment("Base reward value for minor quests")
            .defineInRange("minorRewardValue", CommonConfig.minorRewardValue, 0.01D, 1000.0D);

    private static final ModConfigSpec.IntValue MINOR_REWARD_MIN_ITEMS = BUILDER
            .comment("Minimum number of items given for minor quest rewards")
            .defineInRange("minorRewardMinItems", CommonConfig.minorRewardMinItems, 1, 100);

    private static final ModConfigSpec.IntValue MINOR_REWARD_MAX_ITEMS = BUILDER
            .comment("Maximum number of items given for minor quest rewards")
            .defineInRange("minorRewardMaxItems", CommonConfig.minorRewardMaxItems, 1, 100);

    private static final ModConfigSpec.DoubleValue MAJOR_REWARD_VALUE = BUILDER
            .comment("Base reward value for major quests")
            .defineInRange("majorRewardValue", CommonConfig.majorRewardValue, 0.01D, 1000.0D);

    private static final ModConfigSpec.IntValue MAJOR_REWARD_MIN_ITEMS = BUILDER
            .comment("Minimum number of items given for major quest rewards")
            .defineInRange("majorRewardMinItems", CommonConfig.majorRewardMinItems, 1, 100);

    private static final ModConfigSpec.IntValue MAJOR_REWARD_MAX_ITEMS = BUILDER
            .comment("Maximum number of items given for major quest rewards")
            .defineInRange("majorRewardMaxItems", CommonConfig.minorRewardMaxItems, 1, 100);

    // Quest type toggles
    private static final ModConfigSpec.BooleanValue ENABLE_ABILITY_QUEST = BUILDER
            .comment("Enable ability quests")
            .define("enableAbilityQuest", CommonConfig.enableAbilityQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_SINGLE_TYPE_QUEST = BUILDER
            .comment("Enable single-type quests")
            .define("enableSingleTypeQuest", CommonConfig.enableSingleTypeQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_DOUBLE_TYPE_QUEST = BUILDER
            .comment("Enable double-type quests")
            .define("enableDoubleTypeQuest", CommonConfig.enableDoubleTypeQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_NATURE_QUEST = BUILDER
            .comment("Enable nature quests")
            .define("enableNatureQuest", CommonConfig.enableNatureQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_MOVE_QUEST = BUILDER
            .comment("Enable move quests")
            .define("enableMoveQuest", CommonConfig.enableMoveQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_SIZE_QUEST = BUILDER
            .comment("Enable size quests")
            .define("enableSizeQuest", CommonConfig.enableSizeQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_BASE_STAT_QUEST = BUILDER
            .comment("Enable base stat total quests")
            .define("enableBaseStatQuest", CommonConfig.enableBaseStatQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_WEIGHT_QUEST = BUILDER
            .comment("Enable weight quests")
            .define("enableWeightQuest", CommonConfig.enableWeightQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_DEX_NAME_QUEST = BUILDER
            .comment("Enable pokedex name quests")
            .define("enableDexNameQuest", CommonConfig.enableDexNameQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_REGION_QUEST = BUILDER
            .comment("Enable region quests")
            .define("enableRegionQuest", CommonConfig.enableRegionQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_BIOME_QUEST = BUILDER
            .comment("Enable biome spawn quests")
            .define("enableBiomeQuest", CommonConfig.enableBiomeQuest);

    private static final ModConfigSpec.BooleanValue ENABLE_EVO_STAGE_QUEST = BUILDER
            .comment("Enable evolution stage quests")
            .define("enableEvoQuest", CommonConfig.enableEvoQuest);

    // Quest difficulty multipliers
    private static final ModConfigSpec.DoubleValue ABILITY_QUEST_DIFFICULTY = BUILDER
            .comment("Ability quest difficulty scale")
            .defineInRange("abilityQuestDifficulty", CommonConfig.abilityQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue SINGLE_TYPE_QUEST_DIFFICULTY = BUILDER
            .comment("Single-type quest difficulty scale")
            .defineInRange("singleTypeQuestDifficulty", CommonConfig.singleTypeQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue DOUBLE_TYPE_QUEST_DIFFICULTY = BUILDER
            .comment("Double-type quest difficulty scale")
            .defineInRange("doubleTypeQuestDifficulty", CommonConfig.doubleTypeQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue NATURE_QUEST_DIFFICULTY = BUILDER
            .comment("Nature quest difficulty scale")
            .defineInRange("natureQuestDifficulty", CommonConfig.natureQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue MOVE_QUEST_DIFFICULTY = BUILDER
            .comment("Move quest difficulty scale")
            .defineInRange("moveQuestDifficulty", CommonConfig.moveQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue SIZE_QUEST_DIFFICULTY = BUILDER
            .comment("Size quest difficulty scale")
            .defineInRange("sizeQuestDifficulty", CommonConfig.sizeQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue BASE_STAT_QUEST_DIFFICULTY = BUILDER
            .comment("Base stat quest difficulty scale")
            .defineInRange("baseStatQuestDifficulty", CommonConfig.baseStatQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_DIFFICULTY = BUILDER
            .comment("Weight quest difficulty scale")
            .defineInRange("weightQuestDifficulty", CommonConfig.weightQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue DEX_NAME_QUEST_DIFFICULTY = BUILDER
            .comment("Dex name quest difficulty scale")
            .defineInRange("dexNameQuestDifficulty", CommonConfig.dexNameQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue REGION_QUEST_DIFFICULTY = BUILDER
            .comment("Region quest difficulty scale")
            .defineInRange("regionQuestDifficulty", CommonConfig.regionQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue BIOME_QUEST_DIFFICULTY = BUILDER
            .comment("Biome quest difficulty scale")
            .defineInRange("biomeQuestDifficulty", CommonConfig.biomeQuestDifficulty, 0.01D, 1000.0D);

    private static final ModConfigSpec.DoubleValue EVO_QUEST_DIFFICULTY = BUILDER
            .comment("Evolution quest difficulty scale")
            .defineInRange("evoQuestDifficulty", CommonConfig.evoQuestDifficulty, 0.01D, 1000.0D);

    // Size quest bounds
    private static final ModConfigSpec.DoubleValue SIZE_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum size factor for size quests")
            .defineInRange("sizeQuestLowerBound", CommonConfig.sizeQuestLowerBound, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue SIZE_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum size factor for size quests")
            .defineInRange("sizeQuestUpperBound", CommonConfig.sizeQuestUpperBound, 0.0D, 100.0D);

    // Weight quest bounds
    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum weight factor for weight quests")
            .defineInRange("weightQuestLowerBound", CommonConfig.weightQuestLowerBound, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum weight factor for weight quests")
            .defineInRange("weightQuestUpperBound", CommonConfig.weightQuestUpperBound, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_MIN_GAP = BUILDER
            .comment("Minimum gap between lower and upper weight bounds")
            .defineInRange("weightQuestMinGap", CommonConfig.weightQuestMinGap, 0.0D, 100.0D);

    private static final ModConfigSpec.DoubleValue WEIGHT_QUEST_MAX_GAP = BUILDER
            .comment("Maximum gap between lower and upper weight bounds")
            .defineInRange("weightQuestMaxGap", CommonConfig.weightQuestMaxGap, 0.0D, 100.0D);

    // Base stat quest bounds
    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_LOWER_BOUND = BUILDER
            .comment("Minimum total base stat value for base stat quests")
            .defineInRange("baseStatQuestLowerBound", CommonConfig.baseStatQuestLowerBound, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_UPPER_BOUND = BUILDER
            .comment("Maximum total base stat value for base stat quests")
            .defineInRange("baseStatQuestUpperBound", CommonConfig.baseStatQuestUpperBound, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_MIN_GAP = BUILDER
            .comment("Minimum difference between lower and upper base stat bounds")
            .defineInRange("baseStatQuestMinGap", CommonConfig.baseStatQuestMinGap, 0, 2000);

    private static final ModConfigSpec.IntValue BASE_STAT_QUEST_MAX_GAP = BUILDER
            .comment("Maximum difference between lower and upper base stat bounds")
            .defineInRange("baseStatQuestMaxGap", CommonConfig.baseStatQuestMaxGap, 0, 2000);


    static final ModConfigSpec SPEC = BUILDER.build();

    private static void loadValues() {
        CommonConfig.rerollTimeSeconds = REROLL_TIME_SECONDS.get();

        CommonConfig.indivRewardValueScale = INDIV_REWARD_VALUE_SCALE.get().floatValue();
        CommonConfig.minorRewardValue = MINOR_REWARD_VALUE.get().floatValue();
        CommonConfig.minorRewardMinItems = MINOR_REWARD_MIN_ITEMS.get();
        CommonConfig.minorRewardMaxItems = MINOR_REWARD_MAX_ITEMS.get();
        CommonConfig.majorRewardValue = MAJOR_REWARD_VALUE.get().floatValue();
        CommonConfig.majorRewardMinItems = MAJOR_REWARD_MIN_ITEMS.get();
        CommonConfig.majorRewardMaxItems = MAJOR_REWARD_MAX_ITEMS.get();

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

        CommonConfig.abilityQuestDifficulty = ABILITY_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.singleTypeQuestDifficulty = SINGLE_TYPE_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.doubleTypeQuestDifficulty = DOUBLE_TYPE_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.natureQuestDifficulty = NATURE_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.moveQuestDifficulty = MOVE_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.sizeQuestDifficulty = SIZE_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.baseStatQuestDifficulty = BASE_STAT_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.weightQuestDifficulty = WEIGHT_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.dexNameQuestDifficulty = DEX_NAME_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.regionQuestDifficulty = REGION_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.biomeQuestDifficulty = BIOME_QUEST_DIFFICULTY.get().floatValue();
        CommonConfig.evoQuestDifficulty = EVO_QUEST_DIFFICULTY.get().floatValue();

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
