package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.interactive.CandyItem;
import com.kreidev.cbmnfieldlab.CommonConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RewardManager {

    private static final List<QuestRewardEntry> indivRewards = new ArrayList<>();
    private static final List<QuestRewardEntry> minorRewards = new ArrayList<>();
    private static final List<QuestRewardEntry> majorRewards = new ArrayList<>();

    public static void addIndivRewards(List<QuestRewardEntry> rewards) {
        indivRewards.addAll(rewards);
    }

    public static void addMinorRewards(List<QuestRewardEntry> rewards) {
        minorRewards.addAll(rewards);
    }

    public static void addMajorRewards(List<QuestRewardEntry> rewards) {
        majorRewards.addAll(rewards);
    }

    public static void clearAll() {
        indivRewards.clear();
        minorRewards.clear();
        majorRewards.clear();
    }

    public static ItemStack getIndivReward(RandomSource randomSource, float difficulty) {
        List<QuestRewardEntry> sortedEntries = indivRewards.stream()  // Just to make sure
                .sorted(Comparator.comparingInt(QuestRewardEntry::weight))
                .toList();

        int totalWeight = 0;
        for (QuestRewardEntry entry : sortedEntries) {
            totalWeight += entry.weight();
        }

        QuestRewardEntry randomEntry = sortedEntries.getFirst();
        int rand = randomSource.nextInt(totalWeight);
        for (QuestRewardEntry entry : sortedEntries) {
            if (rand < entry.weight()) {
                randomEntry = entry;
                break;
            }
            rand -= entry.weight();
        }

        int amount = randomSource.nextInt(randomEntry.minCount(), randomEntry.maxCount()+1);
        return new ItemStack(randomEntry.item(), amount);
    }

    public static List<ItemStack> getMinorRewards(RandomSource randomSource) {
        int xpAmount = randomSource.nextInt(CommonConfig.minorRewardMinXP, CommonConfig.minorRewardMaxXP);
        List<ItemStack> rewardList = new ArrayList<>();

        int largeAmount = xpAmount / CandyItem.DEFAULT_L_CANDY_YIELD;
        xpAmount %= CandyItem.DEFAULT_L_CANDY_YIELD;
        int mediumAmount = xpAmount / CandyItem.DEFAULT_M_CANDY_YIELD;
        xpAmount %= CandyItem.DEFAULT_M_CANDY_YIELD;
        int smallAmount = xpAmount / CandyItem.DEFAULT_S_CANDY_YIELD;

        if (largeAmount > 0)
            rewardList.add(new ItemStack(CobblemonItems.EXPERIENCE_CANDY_L, largeAmount));
        if (mediumAmount > 0)
            rewardList.add(new ItemStack(CobblemonItems.EXPERIENCE_CANDY_M, mediumAmount));
        if (smallAmount > 0)
            rewardList.add(new ItemStack(CobblemonItems.EXPERIENCE_CANDY_S, smallAmount));

        return rewardList;
    }

    public static List<ItemStack> getMajorRewards(RandomSource randomSource) {
        // NOTE: Same alg with indivRewards but a list of items

        List<QuestRewardEntry> sortedEntries = majorRewards.stream()  // Just to make sure
                .sorted(Comparator.comparingInt(QuestRewardEntry::weight))
                .toList();

        int items = randomSource.nextInt(CommonConfig.majorRewardMinItems, CommonConfig.majorRewardMinItems+1);
        List<ItemStack> rewardList = new ArrayList<>();

        for (int i=0; i<items; i++) {
            int totalWeight = 0;
            for (QuestRewardEntry entry : sortedEntries) {
                totalWeight += entry.weight();
            }

            QuestRewardEntry randomEntry = sortedEntries.getFirst();
            int rand = randomSource.nextInt(totalWeight);
            for (QuestRewardEntry entry : sortedEntries) {
                if (rand < entry.weight()) {
                    randomEntry = entry;
                    break;
                }
                rand -= entry.weight();
            }

            int amount = randomSource.nextInt(randomEntry.minCount(), randomEntry.maxCount()+1);
            rewardList.add(new ItemStack(randomEntry.item(), amount));
        }

        return rewardList;
    }

}
