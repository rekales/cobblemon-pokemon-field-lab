package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

// NOTE: Deliberately implemented to only hold 3 quests, might ditch NonNullList
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PlayerQuestContainer {

    private final NonNullList<Quest> quests;
    public int finishedQuests;

    public PlayerQuestContainer(int finishedQuests, Quest quest1, Quest quest2, Quest quest3) {
        this.finishedQuests = finishedQuests;
        this.quests = NonNullList.of(quest1, quest2, quest3);
        PokemonFieldLab.LOGGER.warn(this.quests.toString() + " loaded");
    }

    public PlayerQuestContainer(ServerLevel level) {
        this.finishedQuests = 0;
        this.quests = NonNullList.of(
                Quest.getRandomQuest(level),
                Quest.getRandomQuest(level),
                Quest.getRandomQuest(level)
        );
        PokemonFieldLab.LOGGER.warn(this.quests.toString() + " new");
    }

    public void incrementFinishedQuests() {
        this.finishedQuests++;
    }

    public int getFinishedQuests() {
        return this.finishedQuests;
    }

    public Quest getQuest(int index) {
        if (index > 2 || index < 0) return null;
        return this.quests.get(index);
    }

    // returns -1 if not found
    public int getQuestIndex(Quest quest) {
        for (int i=0; i<3; i++) {
            if (quest.equals(quests.get(i))) return i;
        }
        return -1;
    }

    public boolean hasQuest(Quest quest) {
        return getQuestIndex(quest) != -1;
    }

    public boolean replaceQuest(Quest oldQuest, Quest newQuest) {
        return this.replaceQuest(this.getQuestIndex(oldQuest), newQuest);
    }

    public boolean replaceQuest(int index, Quest newQuest) {
        if (index > 2 || index < 0) return false;
        this.quests.set(index, newQuest);
        return true;
    }

    public static CompoundTag save(CompoundTag tag, HolderLookup.Provider provider, PlayerQuestContainer container) {
        // TODO: sometimes the quest is null, not sure why. This TODO was copied from QuestManager
        // Saves just fine on neoforge, not sure what's wrong on fabric
        // nvm, it also has issues with neoforge, I think the issue is caused by replacing type key
        // nvm nvm, still has issues with fabric
        tag.put("Quest1", Quest.save(new CompoundTag(), provider, container.getQuest(0)));
        tag.put("Quest2", Quest.save(new CompoundTag(), provider, container.getQuest(1)));
        tag.put("Quest3", Quest.save(new CompoundTag(), provider, container.getQuest(2)));
        tag.putInt("FinishedQuests", container.getFinishedQuests());
        return tag;
    }

    public static PlayerQuestContainer load(CompoundTag tag) {
        PokemonFieldLab.LOGGER.warn(tag.toString());
        Quest quest1 = Quest.load(tag.getCompound("Quest1"));
        Quest quest2 = Quest.load(tag.getCompound("Quest2"));
        Quest quest3 = Quest.load(tag.getCompound("Quest3"));
        int finishedQuests = tag.getInt("FinishedQuests");
        return new PlayerQuestContainer(finishedQuests, quest1, quest2, quest3);
    }
}
