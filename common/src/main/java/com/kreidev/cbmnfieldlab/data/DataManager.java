package com.kreidev.cbmnfieldlab.data;

import com.kreidev.cbmnfieldlab.quest.QuestRewardEntry;

import java.util.ArrayList;
import java.util.List;

// TODO: maybe use QuestManager instead?
public class DataManager {

    private static final List<QuestRewardEntry> rewards = new ArrayList<>();

    public static void addQuestRewards(List<QuestRewardEntry> rewards) {
        DataManager.rewards.addAll(rewards);
    }

    public static List<QuestRewardEntry> getRewards() {
        return rewards;
    }

    public static void clear() {

    }
}
