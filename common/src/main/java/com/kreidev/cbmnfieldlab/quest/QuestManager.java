package com.kreidev.cbmnfieldlab.quest;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class QuestManager extends SavedData {

    public static QuestManager INSTANCE;

    private final Map<UUID, PlayerQuestContainer> playerQuests = new HashMap<>();

    public QuestManager() {
        super();
    }

    public QuestManager(CompoundTag tag, HolderLookup.Provider provider) {
        this();

        if (!tag.contains("PlayerCobblemonQuests", CompoundTag.TAG_LIST)) return;

        ListTag list = tag.getList("PlayerCobblemonQuests", Tag.TAG_COMPOUND);
        for(int i = 0; i < list.size(); i++) {
            CompoundTag t = list.getCompound(i);
            UUID id = t.getUUID("UUID");
            PlayerQuestContainer container = PlayerQuestContainer.load(t.getCompound("PlayerQuestContainer"));
            playerQuests.put(id, container);
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, PlayerQuestContainer> entry : playerQuests.entrySet()) {
            CompoundTag t = new CompoundTag();
            t.putUUID("UUID", entry.getKey());
            t.put("PlayerQuestContainer", PlayerQuestContainer.save(new CompoundTag(), provider, entry.getValue()));
            list.add(t);
        }
        tag.put("PlayerCobblemonQuests", list);

        return tag;
    }

    public Map<UUID, PlayerQuestContainer> getPlayerQuests() {
        return playerQuests;
    }

    public static @NotNull PlayerQuestContainer getQuestContainer(ServerPlayer player) {
        PlayerQuestContainer container = INSTANCE.getPlayerQuests().get(player.getUUID());
        if (container == null) {
            container = new PlayerQuestContainer((ServerLevel) player.level());
        }

        return container;
    }

    public static boolean submitQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);

        // TODO: validate

        container.incrementFinishedQuests();

        // TODO: do checks for major reward

        return container.replaceQuest(quest, Quest.getRandomQuest((ServerLevel) player.level()));
    }

    public static boolean rerollQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);

        // TODO: validate

        return container.replaceQuest(quest, Quest.getRandomQuest((ServerLevel) player.level()));
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);
        return container.hasQuest(quest);
    }
}
