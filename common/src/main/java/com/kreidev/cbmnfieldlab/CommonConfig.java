package com.kreidev.cbmnfieldlab;

public class CommonConfig {
    // NOTE: no getter/setter crap, just be mindful when to when it gets set.

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

    public static float sizeQuestLowerBound = 0.5F;
    public static float sizeQuestUpperBound = 2F;

    public static float weightQuestLowerBound = 0.5F;
    public static float weightQuestUpperBound = 2F;
    public static float weightQuestMinGap = 0.5F;
    public static float weightQuestMaxGap = 1F;

    public static int baseStatQuestLowerBound = 200;
    public static int baseStatQuestUpperBound = 400;
    public static int baseStatQuestMinGap = 30;
    public static int baseStatQuestMaxGap = 80;

}
