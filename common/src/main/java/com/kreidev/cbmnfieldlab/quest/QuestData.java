package com.kreidev.cbmnfieldlab.quest;

import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface QuestData {
    List<Quest> getQuests(ServerPlayer player);
    boolean replaceQuest(ServerPlayer player, Quest quest);
    boolean hasQuest(ServerPlayer player, Quest quest);
}