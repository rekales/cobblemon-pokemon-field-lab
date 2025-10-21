package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class SizeQuest extends Quest {

    public final float size;

    public SizeQuest(ServerLevel level, float size) {
        super(Type.SIZE, level);
        this.size = size;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        // NOTE: didn't find any "size" attribute but I did find height
        return pokemon.getForm().getHeight() < this.size;
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
        return new SizeQuest(level, threshold);
    }
}
