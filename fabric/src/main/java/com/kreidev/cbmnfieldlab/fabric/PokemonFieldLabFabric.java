package com.kreidev.cbmnfieldlab.fabric;

import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

public class PokemonFieldLabFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PokemonFieldLab.init();

        loadConfigs();
    }

    private static void loadConfigs() {
        MidnightConfig.init(PokemonFieldLab.MOD_ID, CommonConfigFabric.class);

        CommonConfig.rerollTimeSeconds = CommonConfigFabric.REROLL_TIME_SECONDS;

        CommonConfig.enableAbilityQuest = CommonConfigFabric.ENABLE_ABILITY_QUEST;
        CommonConfig.enableSingleTypeQuest = CommonConfigFabric.ENABLE_SINGLE_TYPE_QUEST;
        CommonConfig.enableDoubleTypeQuest = CommonConfigFabric.ENABLE_DOUBLE_TYPE_QUEST;
        CommonConfig.enableNatureQuest = CommonConfigFabric.ENABLE_NATURE_QUEST;
        CommonConfig.enableMoveQuest = CommonConfigFabric.ENABLE_MOVE_QUEST;
        CommonConfig.enableSizeQuest = CommonConfigFabric.ENABLE_SIZE_QUEST;
        CommonConfig.enableBaseStatQuest = CommonConfigFabric.ENABLE_BASE_STAT_QUEST;
        CommonConfig.enableWeightQuest = CommonConfigFabric.ENABLE_WEIGHT_QUEST;

        CommonConfig.sizeQuestLowerBound = CommonConfigFabric.SIZE_QUEST_LOWER_BOUND;
        CommonConfig.sizeQuestUpperBound = CommonConfigFabric.SIZE_QUEST_UPPER_BOUND;

        CommonConfig.weightQuestLowerBound = CommonConfigFabric.WEIGHT_QUEST_LOWER_BOUND;
        CommonConfig.weightQuestUpperBound = CommonConfigFabric.WEIGHT_QUEST_UPPER_BOUND;
        CommonConfig.weightQuestMinGap = CommonConfigFabric.WEIGHT_QUEST_MIN_GAP;
        CommonConfig.weightQuestMaxGap = CommonConfigFabric.WEIGHT_QUEST_MAX_GAP;

        CommonConfig.baseStatQuestLowerBound = CommonConfigFabric.BASE_STAT_QUEST_LOWER_BOUND;
        CommonConfig.baseStatQuestUpperBound = CommonConfigFabric.BASE_STAT_QUEST_UPPER_BOUND;
        CommonConfig.baseStatQuestMinGap = CommonConfigFabric.BASE_STAT_QUEST_MIN_GAP;
        CommonConfig.baseStatQuestMaxGap = CommonConfigFabric.BASE_STAT_QUEST_MAX_GAP;
    }
}
