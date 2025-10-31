package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import eu.midnightdust.lib.config.MidnightConfig;

public class CommonConfigFabric extends MidnightConfig {
    public static final String COMMON = "common";

    @Entry(category = COMMON, min = 1, max = 86400)
    public static int REROLL_TIME_SECONDS = 300;

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
