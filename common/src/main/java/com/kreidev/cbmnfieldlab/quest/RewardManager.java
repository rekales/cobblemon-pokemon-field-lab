package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
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

//    public static void checkChances(RandomSource randomSource) {
//        PokemonFieldLab.LOGGER.info("{}", indivRewards);
//        PokemonFieldLab.LOGGER.info("--------------------");
//
//        List<QuestRewardEntry> sortedEntries = indivRewards.stream()
//                .sorted(Comparator.comparingInt(QuestRewardEntry::weight))
//                .toList();
//
//        int items = 50000;
//
//        Map<Item, Integer> rewardOdds = new HashMap<>();
//
//        for (int i=0; i<items; i++) {
//            int totalWeight = 0;
//            for (QuestRewardEntry entry : sortedEntries) {
//                totalWeight += entry.weight();
//            }
//
//            QuestRewardEntry randomEntry = sortedEntries.getFirst();
//            int rand = randomSource.nextInt(totalWeight);
//            for (QuestRewardEntry entry : sortedEntries) {
//                if (rand < entry.weight()) {
//                    randomEntry = entry;
//                    break;
//                }
//                rand -= entry.weight();
//            }
//
//            Integer odds = rewardOdds.get(randomEntry.item());
//            if (odds == null) {
//                odds = 0;
//            }
//            rewardOdds.put(randomEntry.item(), odds + 1);
//        }
//
//        PokemonFieldLab.LOGGER.info("{}", rewardOdds.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue())
//                .peek(entry -> PokemonFieldLab.LOGGER.info(entry+""))
//                .toList());
//        PokemonFieldLab.LOGGER.info("====================");
//    }

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
        // NOTE: Same alg with indivRewards but a list of 1-3 items, temp
        // TODO: change to no longer need a datapack

        List<QuestRewardEntry> sortedEntries = minorRewards.stream()  // Just to make sure
                .sorted(Comparator.comparingInt(QuestRewardEntry::weight))
                .toList();

        int items = randomSource.nextInt(1, 3);
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

    public static List<ItemStack> getMajorRewards(RandomSource randomSource) {
        // NOTE: Same alg with indivRewards but a list of 4-6 items

        List<QuestRewardEntry> sortedEntries = majorRewards.stream()  // Just to make sure
                .sorted(Comparator.comparingInt(QuestRewardEntry::weight))
                .toList();

        int items = randomSource.nextInt(4, 6);
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
