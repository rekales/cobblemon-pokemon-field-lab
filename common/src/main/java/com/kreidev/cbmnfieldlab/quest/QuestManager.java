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

    private final Map<UUID, List<Quest>> playerQuests = new HashMap<>();

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
            Quest quest1 = Quest.load(t.getCompound("Quest1"));
            Quest quest2 = Quest.load(t.getCompound("Quest2"));
            Quest quest3 = Quest.load(t.getCompound("Quest3"));
            playerQuests.put(id, Arrays.asList(quest1, quest2, quest3));
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, List<Quest>> entry : playerQuests.entrySet()) {
            CompoundTag t = new CompoundTag();
            t.putUUID("UUID", entry.getKey());
            t.put("Quest1", Quest.save(new CompoundTag(), entry.getValue().get(0)));
            t.put("Quest2", Quest.save(new CompoundTag(), entry.getValue().get(1)));
            t.put("Quest3", Quest.save(new CompoundTag(), entry.getValue().get(2)));
            list.add(t);
        }
        tag.put("PlayerCobblemonQuests", list);

        return tag;
    }

    public Map<UUID, List<Quest>> getPlayerQuests() {
        return playerQuests;
    }

    public static List<Quest> getQuests(ServerPlayer player) {
        List<Quest> quests = INSTANCE.getPlayerQuests().get(player.getUUID());
        if (quests == null) {
            quests = new ArrayList<>(3);
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            quests.add(Quest.getRandomQuest((ServerLevel) player.level()));
            INSTANCE.getPlayerQuests().put(player.getUUID(), quests);
            INSTANCE.setDirty();
        }

        // NOTE: maybe validate quests here first?

        return quests;
    }

    public static boolean replaceQuest(ServerPlayer player, Quest quest) {
        List<Quest> quests = INSTANCE.getPlayerQuests().get(player.getUUID());
        for (int i = 0; i < quests.size(); i++) {
            if (quest.equals(quests.get(i))) {
                quests.set(i, Quest.getRandomQuest((ServerLevel) player.level()));
                INSTANCE.setDirty();
                return true;
            }
        }
        return false;
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        List<Quest> quests = INSTANCE.getPlayerQuests().get(player.getUUID());
        for (Quest value : quests) {
            if (quest.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
