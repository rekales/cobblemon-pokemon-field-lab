package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.network.FieldLabNetworkManager;
import com.kreidev.cbmnfieldlab.network.RerollPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
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

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerQuestContainer> STREAM_CODEC =
            FieldLabNetworkManager.fromCodec(CODEC.codec());

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

    public @NotNull Quest firstQuest() {
        return quest1;
    }

    public @NotNull Quest secondQuest() {
        return quest2;
    }

    public @NotNull Quest thirdQuest() {
        return quest3;
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
