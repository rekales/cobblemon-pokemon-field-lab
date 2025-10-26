package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WeightQuest extends Quest {

    public static final MapCodec<WeightQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.FLOAT.fieldOf("quest_lower_weight").forGetter(WeightQuest::getLowerWeight),
            Codec.FLOAT.fieldOf("quest_upper_weight").forGetter(WeightQuest::getUpperWeight)
    ).apply(instance, WeightQuest::new));

    public final float lowerWeight;
    public final float upperWeight;

    // Random Quest
    public WeightQuest(ServerLevel level) {
        super(level);
        float lowerBound = 0.5F;
        float upperBound = 2F;
        float minGap = 0.5F;
        float maxGap = 1;
        this.lowerWeight = level.getRandom().nextFloat() * (upperBound-lowerBound) + lowerBound;
        this.upperWeight = this.lowerWeight + level.getRandom().nextFloat() * (maxGap-minGap) + minGap;
    }

    public WeightQuest(ServerLevel level, float lowerWeight, float upperWeight) {
        super(level);
        this.lowerWeight = lowerWeight;
        this.upperWeight = upperWeight;
    }

    public WeightQuest(long timestamp, ItemStack reward, float lowerWeight, float upperWeight) {
        super(timestamp, reward);
        this.lowerWeight = lowerWeight;
        this.upperWeight = upperWeight;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        float weight = pokemon.getForm().getWeight();
        return this.lowerWeight < weight && weight < this.upperWeight;
    }

    @Override
    public String getModifierString() {
        return this.lowerWeight + " - " + this.upperWeight;
    }

    public float getLowerWeight() {
        return lowerWeight;
    }

    public float getUpperWeight() {
        return upperWeight;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.WEIGHT;
    }
}
