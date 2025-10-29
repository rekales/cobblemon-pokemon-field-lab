package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StatQuest extends Quest {

    public static final MapCodec<StatQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.INT.fieldOf("quest_lower_stat").forGetter(StatQuest::getLowerStatTotal),
            Codec.INT.fieldOf("quest_upper_stat").forGetter(StatQuest::getUpperStatTotal)
    ).apply(instance, StatQuest::new));

    public final int upperStatTotal;
    public final int lowerStatTotal;

    // Random Quest
    public StatQuest(ServerLevel level) {
        super(level);
        int lowerBound = CommonConfig.baseStatQuestLowerBound;
        int upperBound = CommonConfig.baseStatQuestUpperBound;
        int minGap = CommonConfig.baseStatQuestMinGap;
        int maxGap = CommonConfig.baseStatQuestMaxGap;
        this.lowerStatTotal = level.getRandom().nextInt(lowerBound, upperBound);
        this.upperStatTotal = this.lowerStatTotal + level.getRandom().nextInt(minGap, maxGap);
    }

    @SuppressWarnings("unused")
    public StatQuest(ServerLevel level, int lowerStatTotal, int upperStatTotal) {
        super(level);
        this.lowerStatTotal = lowerStatTotal;
        this.upperStatTotal = upperStatTotal;
    }

    public StatQuest(long timestamp, ItemStack reward, int lowerStatTotal, int upperStatTotal) {
        super(timestamp, reward);
        this.lowerStatTotal = lowerStatTotal;
        this.upperStatTotal = upperStatTotal;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        int stats = 0;
        Map<Stat, Integer> baseStats = pokemon.getSpecies().getBaseStats();
        stats += baseStats.get(Stats.HP);
        stats += baseStats.get(Stats.ATTACK);
        stats += baseStats.get(Stats.DEFENCE);
        stats += baseStats.get(Stats.SPECIAL_ATTACK);
        stats += baseStats.get(Stats.SPECIAL_DEFENCE);
        stats += baseStats.get(Stats.SPEED);
        return this.lowerStatTotal < stats && stats < this.upperStatTotal;
    }

    @Override
    public String getModifierString() {
        return this.lowerStatTotal + "-" + this.upperStatTotal;
    }

    public int getUpperStatTotal() {
        return upperStatTotal;
    }

    public int getLowerStatTotal() {
        return lowerStatTotal;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.BASE_STAT;
    }
}
