package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// NOTE: Deliberately implemented to only hold 3 quests
// I feel like it's better to do it like this so I can annotate with @NonNull
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PlayerQuestContainer {

    @NotNull public Quest quest1;
    @NotNull public Quest quest2;
    @NotNull public Quest quest3;
    public int finishedQuests;

    public PlayerQuestContainer(int finishedQuests, @NotNull Quest quest1, @NotNull Quest quest2, @NotNull Quest quest3) {
        this.finishedQuests = finishedQuests;
        this.quest1 = quest1;
        this.quest2 = quest2;
        this.quest3 = quest3;
    }

    public PlayerQuestContainer(ServerLevel level) {
        this.finishedQuests = 0;
        this.quest1 = Quest.getRandomQuest(level);
        this.quest2 = Quest.getRandomQuest(level);
        this.quest3 = Quest.getRandomQuest(level);
    }

    public void incrementFinishedQuests() {
        this.finishedQuests++;
    }

    public int getFinishedQuests() {
        return this.finishedQuests;
    }

    public @Nullable Quest getQuest(int index) {
        return switch (index) {
            case 0 -> this.quest1;
            case 1 -> this.quest2;
            case 2 -> this.quest3;
            default -> null;
        };
    }

    // returns -1 if not found
    public int getQuestIndex(Quest quest) {
        if (quest.equals(quest1)) return 0;
        if (quest.equals(quest2)) return 1;
        if (quest.equals(quest3)) return 2;
        return -1;
    }

    public boolean hasQuest(Quest quest) {
        return getQuestIndex(quest) != -1;
    }

    public boolean replaceQuest(Quest oldQuest, Quest newQuest) {
        return this.replaceQuest(this.getQuestIndex(oldQuest), newQuest);
    }

    public boolean replaceQuest(int index, Quest newQuest) {
        switch (index) {
            case 0 :
                this.quest1 = newQuest;
                return true;
            case 1 :
                this.quest2 = newQuest;
                return true;
            case 2 :
                this.quest3 = newQuest;
                return true;
        };
        return false;
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
