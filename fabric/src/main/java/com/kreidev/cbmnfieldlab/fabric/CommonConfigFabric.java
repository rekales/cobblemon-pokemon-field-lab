package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import eu.midnightdust.lib.config.MidnightConfig;

// NOTE: setting default values like this could probably cause issues
public class CommonConfigFabric extends MidnightConfig {
    public static final String COMMON = "common";

    @Entry(category = COMMON, min = 1, max = 86400)
    public static int REROLL_TIME_SECONDS = CommonConfig.rerollTimeSeconds;

    // Quest reward bounds
    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float INDIV_REWARD_VALUE_SCALE = CommonConfig.indivRewardValueScale;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MINOR_REWARD_VALUE = CommonConfig.minorRewardValue;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MINOR_REWARD_MIN_ITEMS = CommonConfig.minorRewardMinItems;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MINOR_REWARD_MAX_ITEMS = CommonConfig.minorRewardMaxItems;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MAJOR_REWARD_VALUE = CommonConfig.majorRewardValue;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MAJOR_REWARD_MIN_ITEMS = CommonConfig.majorRewardMinItems;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MAJOR_REWARD_MAX_ITEMS = CommonConfig.majorRewardMaxItems;

    // Quest type toggles
    @Entry(category = COMMON)
    public static boolean ENABLE_ABILITY_QUEST = CommonConfig.enableAbilityQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_SINGLE_TYPE_QUEST = CommonConfig.enableSingleTypeQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_DOUBLE_TYPE_QUEST = CommonConfig.enableDoubleTypeQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_NATURE_QUEST = CommonConfig.enableNatureQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_MOVE_QUEST = CommonConfig.enableMoveQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_SIZE_QUEST = CommonConfig.enableSizeQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_BASE_STAT_QUEST = CommonConfig.enableBaseStatQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_WEIGHT_QUEST = CommonConfig.enableWeightQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_DEX_NAME_QUEST = CommonConfig.enableDexNameQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_REGION_QUEST = CommonConfig.enableRegionQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_BIOME_QUEST = CommonConfig.enableBiomeQuest;

    @Entry(category = COMMON)
    public static boolean ENABLE_EVO_STAGE_QUEST = CommonConfig.enableEvoQuest;

    // Quest difficulty multipliers
    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float ABILITY_QUEST_DIFFICULTY = CommonConfig.abilityQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float SINGLE_TYPE_QUEST_DIFFICULTY = CommonConfig.singleTypeQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float DOUBLE_TYPE_QUEST_DIFFICULTY = CommonConfig.doubleTypeQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float NATURE_QUEST_DIFFICULTY = CommonConfig.natureQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MOVE_QUEST_DIFFICULTY = CommonConfig.moveQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float SIZE_QUEST_DIFFICULTY = CommonConfig.sizeQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float BASE_STAT_QUEST_DIFFICULTY = CommonConfig.baseStatQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float WEIGHT_QUEST_DIFFICULTY = CommonConfig.weightQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float DEX_NAME_QUEST_DIFFICULTY = CommonConfig.dexNameQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float REGION_QUEST_DIFFICULTY = CommonConfig.regionQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float BIOME_QUEST_DIFFICULTY = CommonConfig.biomeQuestDifficulty;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float EVO_QUEST_DIFFICULTY = CommonConfig.evoQuestDifficulty;

    // Size quest bounds
    @Entry(category = COMMON, min = 0, max = 100)
    public static float SIZE_QUEST_LOWER_BOUND = CommonConfig.sizeQuestLowerBound;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float SIZE_QUEST_UPPER_BOUND = CommonConfig.sizeQuestUpperBound;

    // Weight quest bounds
    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_LOWER_BOUND = CommonConfig.weightQuestLowerBound;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_UPPER_BOUND = CommonConfig.weightQuestUpperBound;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_MIN_GAP = CommonConfig.weightQuestMinGap;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_MAX_GAP = CommonConfig.weightQuestMaxGap;

    // Base stat quest bounds
    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_LOWER_BOUND = CommonConfig.baseStatQuestLowerBound;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_UPPER_BOUND = CommonConfig.baseStatQuestUpperBound;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_MIN_GAP = CommonConfig.baseStatQuestMinGap;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_MAX_GAP = CommonConfig.baseStatQuestMaxGap;


    static void loadConfigs() {
        MidnightConfig.init(PokemonFieldLab.MOD_ID, CommonConfigFabric.class);

        CommonConfig.rerollTimeSeconds = REROLL_TIME_SECONDS;

        CommonConfig.indivRewardValueScale = INDIV_REWARD_VALUE_SCALE;
        CommonConfig.minorRewardValue = MINOR_REWARD_VALUE;
        CommonConfig.minorRewardMinItems = MINOR_REWARD_MIN_ITEMS;
        CommonConfig.minorRewardMaxItems = MINOR_REWARD_MAX_ITEMS;
        CommonConfig.majorRewardValue = MAJOR_REWARD_VALUE;
        CommonConfig.majorRewardMinItems = MAJOR_REWARD_MIN_ITEMS;
        CommonConfig.majorRewardMaxItems = MAJOR_REWARD_MAX_ITEMS;

        CommonConfig.enableAbilityQuest = ENABLE_ABILITY_QUEST;
        CommonConfig.enableSingleTypeQuest = ENABLE_SINGLE_TYPE_QUEST;
        CommonConfig.enableDoubleTypeQuest = ENABLE_DOUBLE_TYPE_QUEST;
        CommonConfig.enableNatureQuest = ENABLE_NATURE_QUEST;
        CommonConfig.enableMoveQuest = ENABLE_MOVE_QUEST;
        CommonConfig.enableSizeQuest = ENABLE_SIZE_QUEST;
        CommonConfig.enableBaseStatQuest = ENABLE_BASE_STAT_QUEST;
        CommonConfig.enableWeightQuest = ENABLE_WEIGHT_QUEST;
        CommonConfig.enableDexNameQuest = ENABLE_DEX_NAME_QUEST;
        CommonConfig.enableRegionQuest = ENABLE_REGION_QUEST;
        CommonConfig.enableBiomeQuest = ENABLE_BIOME_QUEST;
        CommonConfig.enableEvoQuest = ENABLE_EVO_STAGE_QUEST;

        CommonConfig.abilityQuestDifficulty = ABILITY_QUEST_DIFFICULTY;
        CommonConfig.singleTypeQuestDifficulty = SINGLE_TYPE_QUEST_DIFFICULTY;
        CommonConfig.doubleTypeQuestDifficulty = DOUBLE_TYPE_QUEST_DIFFICULTY;
        CommonConfig.natureQuestDifficulty = NATURE_QUEST_DIFFICULTY;
        CommonConfig.moveQuestDifficulty = MOVE_QUEST_DIFFICULTY;
        CommonConfig.sizeQuestDifficulty = SIZE_QUEST_DIFFICULTY;
        CommonConfig.baseStatQuestDifficulty = BASE_STAT_QUEST_DIFFICULTY;
        CommonConfig.weightQuestDifficulty = WEIGHT_QUEST_DIFFICULTY;
        CommonConfig.dexNameQuestDifficulty = DEX_NAME_QUEST_DIFFICULTY;
        CommonConfig.regionQuestDifficulty = REGION_QUEST_DIFFICULTY;
        CommonConfig.biomeQuestDifficulty = BIOME_QUEST_DIFFICULTY;
        CommonConfig.evoQuestDifficulty = EVO_QUEST_DIFFICULTY;

        CommonConfig.sizeQuestLowerBound = SIZE_QUEST_LOWER_BOUND;
        CommonConfig.sizeQuestUpperBound = SIZE_QUEST_UPPER_BOUND;

        CommonConfig.weightQuestLowerBound = WEIGHT_QUEST_LOWER_BOUND;
        CommonConfig.weightQuestUpperBound = WEIGHT_QUEST_UPPER_BOUND;
        CommonConfig.weightQuestMinGap = WEIGHT_QUEST_MIN_GAP;
        CommonConfig.weightQuestMaxGap = WEIGHT_QUEST_MAX_GAP;

        CommonConfig.baseStatQuestLowerBound = BASE_STAT_QUEST_LOWER_BOUND;
        CommonConfig.baseStatQuestUpperBound = BASE_STAT_QUEST_UPPER_BOUND;
        CommonConfig.baseStatQuestMinGap = BASE_STAT_QUEST_MIN_GAP;
        CommonConfig.baseStatQuestMaxGap = BASE_STAT_QUEST_MAX_GAP;
    }
}
