package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.data.DataManager;
import com.kreidev.cbmnfieldlab.quest.type.*;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

@SuppressWarnings("unused")
public abstract class Quest {
    public static final Codec<Quest> CODEC = QuestType.REGISTRY.byNameCodec()
            .dispatch("type", Quest::getType, QuestType::codec);

    public final long timeStamp;  // Timestamp ticks for when the quest started
    @NotNull public final QuestType<?> type;
    @NotNull public final ItemStack reward;

    public Quest(ServerLevel level) {
        this.type = this.getType();
        this.timeStamp = level.getGameTime();
        // TODO: generate reward relative to getDifficulty()

        List<QuestRewardEntry> rewardList = DataManager.getRewards();
        QuestRewardEntry rewardEntry = rewardList.get(level.getRandom().nextInt(rewardList.size()));
        int randAmount = level.getRandom().nextInt(rewardEntry.minCount(), rewardEntry.maxCount());
        this.reward = new ItemStack(rewardEntry.item(), randAmount);
    }

    // NOTE: In case there's a need to override reward
    public Quest(ServerLevel level, @NotNull ItemStack reward) {
        this.type = this.getType();
        this.timeStamp = level.getGameTime();
        this.reward = reward;
    }

    // NOTE: for deserialization purposes
    protected Quest(long timeStamp, @NotNull ItemStack reward) {
        this.type = this.getType();
        this.timeStamp = timeStamp;
        this.reward = reward;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "timeStamp=" + timeStamp +
                ", type=" + type +
                ", modifier=" + this.getModifierString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quest quest)) return false;
        // good enough for practical purposes
        return this.type == quest.type && this.timeStamp == quest.timeStamp;
    }

    public abstract boolean isEligible(@NotNull Pokemon pokemon);

    public abstract String getModifierString();

    public float getDifficulty() {
        return 1;
    }

    public abstract @NotNull QuestType<?> getType();

    public @NotNull ItemStack getReward() {
     return this.reward;
    }

    // TODO: missing biome quest
    // TODO: missing evo quest
    // TODO: maybe replace with dynamic registration or feature flags whatever that may be
    // NOTE: The ServerLevel is really just to get the timestamp
    public static Quest getRandomQuest(ServerLevel level) {
        List<QuestType<?>> enabledQuestTypes = new ArrayList<>();
        if (CommonConfig.enableAbilityQuest)    enabledQuestTypes.add(QuestTypes.ABILITY);
        if (CommonConfig.enableSingleTypeQuest) enabledQuestTypes.add(QuestTypes.SINGLE_TYPE);
        if (CommonConfig.enableDoubleTypeQuest) enabledQuestTypes.add(QuestTypes.DOUBLE_TYPE);
        if (CommonConfig.enableNatureQuest)     enabledQuestTypes.add(QuestTypes.NATURE);
        if (CommonConfig.enableMoveQuest)       enabledQuestTypes.add(QuestTypes.MOVE);
        if (CommonConfig.enableSizeQuest)       enabledQuestTypes.add(QuestTypes.SIZE);
        if (CommonConfig.enableBaseStatQuest)   enabledQuestTypes.add(QuestTypes.BASE_STAT);
        if (CommonConfig.enableWeightQuest)     enabledQuestTypes.add(QuestTypes.WEIGHT);
//        if (CommonConfig.enableDexNameQuest)    enabledQuestTypes.add(QuestTypes.DEX_NAME);
//        if (CommonConfig.enableRegionQuest)    enabledQuestTypes.add(QuestTypes.REGION);

        if (enabledQuestTypes.isEmpty()) return new TypeQuest(level);
        int randIndex = level.getRandom().nextInt(enabledQuestTypes.size());
        return enabledQuestTypes.get(randIndex).randomQuestFactory().apply(level);
    }
}
