package com.kreidev.cbmnfieldlab;

public class CommonConfig {
    // NOTE: no getter/setter crap, just be mindful when to when it gets set.
    // TODO: use this values for defaults instead of setting individually per platform
    // TODO: organize configs on both platforms

    public static int rerollTimeSeconds = 300;

    public static float indivRewardValueScale = 2.0F;

    public static float minorRewardValue = 4.0F;
    public static int minorRewardMinItems = 1;
    public static int minorRewardMaxItems = 2;

    public static float majorRewardValue = 8.0F;
    public static int majorRewardMinItems = 3;
    public static int majorRewardMaxItems = 6;

    public static boolean enableAbilityQuest = true;
    public static boolean enableSingleTypeQuest = true;
    public static boolean enableDoubleTypeQuest = true;
    public static boolean enableNatureQuest = true;
    public static boolean enableMoveQuest = true;
    public static boolean enableSizeQuest = true;
    public static boolean enableBaseStatQuest = true;
    public static boolean enableWeightQuest = true;
    public static boolean enableDexNameQuest = true;
    public static boolean enableRegionQuest = true;
    public static boolean enableBiomeQuest = true;
    public static boolean enableEvoQuest = true;

    public static float abilityQuestDifficulty = 1.0F;
    public static float singleTypeQuestDifficulty = 1.0F;
    public static float doubleTypeQuestDifficulty = 1.0F;
    public static float natureQuestDifficulty = 1.0F;
    public static float moveQuestDifficulty = 1.0F;
    public static float sizeQuestDifficulty = 1.0F;
    public static float baseStatQuestDifficulty = 1.0F;
    public static float weightQuestDifficulty = 1.0F;
    public static float dexNameQuestDifficulty = 1.0F;
    public static float regionQuestDifficulty = 1.0F;
    public static float biomeQuestDifficulty = 1.0F;
    public static float evoQuestDifficulty = 1.0F;

    public static float sizeQuestLowerBound = 5F;
    public static float sizeQuestUpperBound = 20F;

    public static float weightQuestLowerBound = 10F;
    public static float weightQuestUpperBound = 600F;
    public static float weightQuestMinGap = 20F;
    public static float weightQuestMaxGap = 80F;

    public static int baseStatQuestLowerBound = 200;
    public static int baseStatQuestUpperBound = 400;
    public static int baseStatQuestMinGap = 30;
    public static int baseStatQuestMaxGap = 80;

}
