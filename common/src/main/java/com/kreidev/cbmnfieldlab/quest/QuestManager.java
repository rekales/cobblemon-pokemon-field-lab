package com.kreidev.cbmnfieldlab.quest;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class QuestManager {

    public static List<Quest> getQuests(ServerPlayer player) {
        QuestData questData = getInstance();
        return questData.getQuests(player);
    }

    public static boolean replaceQuest(ServerPlayer player, Quest quest) {
        QuestData questData = getInstance();
        return questData.replaceQuest(player, quest);
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        QuestData questData = getInstance();
        return questData.hasQuest(player, quest);
    }

    @ExpectPlatform
    public static QuestData getInstance() {
        throw new AssertionError();
    }
}
