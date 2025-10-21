package com.kreidev.cbmnfieldlab.quest;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class QuestManager {

    private static final Map<UUID, List<Quest>> playerQuests = new HashMap<>();

    public static List<Quest> getQuests(ServerPlayer player) {
        List<Quest> quests = playerQuests.get(player.getUUID());
        if (quests == null) {
            quests = new ArrayList<>(3);
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            playerQuests.put(player.getUUID(), quests);
        }

        // NOTE: maybe validate quests here first?

        return quests;
    }

    public static boolean replaceQuest(ServerPlayer player, Quest quest) {
        List<Quest> quests = playerQuests.get(player.getUUID());
        for (int i = 0; i < quests.size(); i++) {
            if (quest.equals(quests.get(i))) {
                quests.set(i, Quest.getRandomQuest((ServerLevel) player.level()));
                return true;
            }
        }
        return false;
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        List<Quest> quests = playerQuests.get(player.getUUID());
        for (Quest value : quests) {
            if (quest.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static void saveQuests() {

    }

    public static void loadQuests() {

    }
}
