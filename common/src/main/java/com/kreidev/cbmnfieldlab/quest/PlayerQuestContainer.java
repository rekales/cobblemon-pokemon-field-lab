package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtException;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// NOTE: Deliberately implemented to only hold 3 quests
// I feel like it's better to do it like this so that I can annotate with @NonNull
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PlayerQuestContainer {

    public static final MapCodec<PlayerQuestContainer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("finishedQuest").forGetter(PlayerQuestContainer::getFinishedQuests),
            Quest.CODEC.fieldOf("quest1").forGetter(container->container.getQuest(0)),
            Quest.CODEC.fieldOf("quest2").forGetter(container->container.getQuest(1)),
            Quest.CODEC.fieldOf("quest3").forGetter(container->container.getQuest(2))
    ).apply(instance, PlayerQuestContainer::new));

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

    public boolean replaceQuest(Quest oldQuest, @NotNull Quest newQuest) {
        return this.replaceQuest(this.getQuestIndex(oldQuest), newQuest);
    }

    public boolean replaceQuest(int index, @NotNull Quest newQuest) {
        return switch (index) {
            case 0 -> {
                this.quest1 = newQuest;
                yield true;
            }
            case 1 -> {
                this.quest2 = newQuest;
                yield true;
            }
            case 2 -> {
                this.quest3 = newQuest;
                yield true;
            }
            default -> false;
        };
    }

    public static CompoundTag save(CompoundTag tag, HolderLookup.Provider provider, PlayerQuestContainer container) {
        // TODO: sometimes the quest is null, not sure why. This TODO was copied from QuestManager
        // Saves just fine on neoforge, not sure what's wrong on fabric
        // nvm, it also has issues with neoforge, I think the issue is caused by replacing type key
        // nvm nvm, still has issues with fabric
        tag.put("Quest1", Quest.save(new CompoundTag(), provider, container.quest1));
        tag.put("Quest2", Quest.save(new CompoundTag(), provider, container.quest2));
        tag.put("Quest3", Quest.save(new CompoundTag(), provider, container.quest3));
        tag.putInt("FinishedQuests", container.getFinishedQuests());
        return tag;
    }

    public static PlayerQuestContainer load(CompoundTag tag) {
        PokemonFieldLab.LOGGER.warn(tag.toString());
        Quest quest1 = Quest.load(tag.getCompound("Quest1"));
        Quest quest2 = Quest.load(tag.getCompound("Quest2"));
        Quest quest3 = Quest.load(tag.getCompound("Quest3"));
        int finishedQuests = tag.getInt("FinishedQuests");
        if (quest1==null || quest2==null || quest3==null) {
            PokemonFieldLab.LOGGER.info("{}, {}, {}", quest1, quest2, quest3);
            throw new NbtException("Null Quest Detected");
        }
        return new PlayerQuestContainer(finishedQuests, quest1, quest2, quest3);
    }

    @Override
    public String toString() {
        return "PlayerQuestContainer{" +
                "quest1=" + quest1 +
                ", quest2=" + quest2 +
                ", quest3=" + quest3 +
                ", finishedQuests=" + finishedQuests +
                '}';
    }
}
