package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
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

    @Override
    public String getModifierString() {
        return Float.toString(size);
    }

    public static Quest createRandom(ServerLevel level) {
        float lowerBound = 0.5F;
        float upperBound = 2F;
        float threshold = level.getRandom().nextFloat() * (upperBound-lowerBound) + lowerBound;
        return new SizeQuest(level, threshold);
    }
}
