package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import eu.midnightdust.lib.config.MidnightConfig;

public class CommonConfigFabric extends MidnightConfig {
    public static final String COMMON = "common";

    @Entry(category = COMMON, min = 1, max = 86400)
    public static int REROLL_TIME_SECONDS = 300;

    // Quest reward bounds
    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float INDIV_REWARD_VALUE_SCALE = 2.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MINOR_REWARD_VALUE = 4.0F;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MINOR_REWARD_MIN_ITEMS = 1;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MINOR_REWARD_MAX_ITEMS = 2;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MAJOR_REWARD_VALUE = 8.0F;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MAJOR_REWARD_MIN_ITEMS = 3;

    @Entry(category = COMMON, min = 1, max = 100)
    public static int MAJOR_REWARD_MAX_ITEMS = 6;

    // Quest type toggles
    @Entry(category = COMMON)
    public static boolean ENABLE_ABILITY_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_SINGLE_TYPE_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_DOUBLE_TYPE_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_NATURE_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_MOVE_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_SIZE_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_BASE_STAT_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_WEIGHT_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_DEX_NAME_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_REGION_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_BIOME_QUEST = true;

    @Entry(category = COMMON)
    public static boolean ENABLE_EVO_STAGE_QUEST = true;

    // Quest difficulty multipliers
    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float ABILITY_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float SINGLE_TYPE_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float DOUBLE_TYPE_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float NATURE_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float MOVE_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float SIZE_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float BASE_STAT_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float WEIGHT_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float DEX_NAME_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float REGION_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float BIOME_QUEST_DIFFICULTY = 1.0F;

    @Entry(category = COMMON, min = 0.01, max = 1000)
    public static float EVO_QUEST_DIFFICULTY = 1.0F;

    // Size quest bounds
    @Entry(category = COMMON, min = 0, max = 100)
    public static float SIZE_QUEST_LOWER_BOUND = 0.5F;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float SIZE_QUEST_UPPER_BOUND = 2.0F;

    // Weight quest bounds
    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_LOWER_BOUND = 0.5F;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_UPPER_BOUND = 2.0F;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_MIN_GAP = 0.5F;

    @Entry(category = COMMON, min = 0, max = 100)
    public static float WEIGHT_QUEST_MAX_GAP = 1.0F;

    // Base stat quest bounds
    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_LOWER_BOUND = 200;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_UPPER_BOUND = 400;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_MIN_GAP = 30;

    @Entry(category = COMMON, min = 0, max = 2000)
    public static int BASE_STAT_QUEST_MAX_GAP = 80;


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
