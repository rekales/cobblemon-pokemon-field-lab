package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class WeightQuest extends Quest {

    public final float weight;

    public WeightQuest(ServerLevel level, float weight) {
        super(Type.WEIGHT, level);
        this.weight = weight;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getForm().getWeight() < this.weight;
    }

    public CompoundTag save(CompoundTag tag) {

        return tag;
    }

    public Quest load(CompoundTag tag) {
        return null;
    }

    public static Quest createRandom(ServerLevel level) {
        float lowerBound = 0.5F;
        float upperBound = 2F;
        float threshold = level.getRandom().nextFloat() * (upperBound-lowerBound) + lowerBound;
        return new WeightQuest(level, threshold);
    }
}
