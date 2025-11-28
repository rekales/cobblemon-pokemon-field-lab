package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.CommonConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        QuestRewardEntry rewardEntry = indivRewards.get(randomSource.nextInt(indivRewards.size()));
        float value = difficulty * CommonConfig.indivRewardValueScale;
        int amount = rewardEntry.minCount();
        while (amount < rewardEntry.maxCount() && value > rewardEntry.cost()*amount) {
            amount++;
        }

        return new ItemStack(rewardEntry.item(), amount);
    }

    public static List<ItemStack> getMinorRewards(RandomSource randomSource) {
        // Get a random sample from the list of possible rewards
        int items = Math.min(CommonConfig.minorRewardMinItems, minorRewards.size());
        int maxItems = Math.min(CommonConfig.minorRewardMaxItems, minorRewards.size());
        items = items <= maxItems ? items : randomSource.nextInt(items, maxItems);

        List<QuestRewardEntry> sample = new ArrayList<>(minorRewards);
        Collections.shuffle(sample);
        sample = sample.subList(0, items);
        List<MutablePair<Integer, QuestRewardEntry>> entries = sample.stream()
                .map(entry->new MutablePair<>(entry.minCount(), entry))
                .toList();

        // Set entry amounts
        double totalCost = entries.stream()
                .mapToDouble(pair->pair.getLeft()*pair.getRight().cost())
                .sum();
        while (totalCost < CommonConfig.minorRewardValue) {
            List<MutablePair<Integer, QuestRewardEntry>> availableEntries = entries.stream()
                    .filter(pair->pair.getLeft()<pair.getRight().maxCount())
                    .toList();

            if (availableEntries.isEmpty()) break;
            MutablePair<Integer, QuestRewardEntry> availablePair = availableEntries.get(randomSource.nextInt(availableEntries.size()));
            availablePair.setLeft(availablePair.getLeft()+1);

            totalCost = entries.stream()
                    .mapToDouble(pair->pair.getLeft()*pair.getRight().cost())
                    .sum();
        }

        return entries.stream()
                .map(pair->new ItemStack(pair.getRight().item(), pair.getLeft()))
                .toList();
    }

    public static List<ItemStack> getMajorRewards(RandomSource randomSource) {
        // Get a random sample from the list of possible rewards
        int items = Math.min(CommonConfig.majorRewardMinItems, majorRewards.size());
        int maxItems = Math.min(CommonConfig.majorRewardMaxItems, majorRewards.size());
        items = items <= maxItems ? items : randomSource.nextInt(items, maxItems);

        List<QuestRewardEntry> sample = new ArrayList<>(majorRewards);
        Collections.shuffle(sample);
        sample = sample.subList(0, items);
        List<MutablePair<Integer, QuestRewardEntry>> entries = sample.stream()
                .map(entry->new MutablePair<>(entry.minCount(), entry))
                .toList();

        // Set entry amounts
        double totalCost = entries.stream()
                .mapToDouble(pair->pair.getLeft()*pair.getRight().cost())
                .sum();
        while (totalCost < CommonConfig.majorRewardValue) {
            List<MutablePair<Integer, QuestRewardEntry>> availableEntries = entries.stream()
                    .filter(pair->pair.getLeft()<pair.getRight().maxCount())
                    .toList();

            if (availableEntries.isEmpty()) break;
            MutablePair<Integer, QuestRewardEntry> availablePair = availableEntries.get(randomSource.nextInt(availableEntries.size()));
            availablePair.setLeft(availablePair.getLeft()+1);

            totalCost = entries.stream()
                    .mapToDouble(pair->pair.getLeft()*pair.getRight().cost())
                    .sum();
        }

        return entries.stream()
                .map(pair->new ItemStack(pair.getRight().item(), pair.getLeft()))
                .toList();
    }

}
