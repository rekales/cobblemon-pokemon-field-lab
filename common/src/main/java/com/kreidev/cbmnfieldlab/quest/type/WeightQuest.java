package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
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

    @Override
    public String getModifierString() {
        return Float.toString(weight);
    }

    @Override
    public QuestType<?> getType() {
        return QuestTypes.NATURE;
    }


    public static Quest createRandom(ServerLevel level) {
        float lowerBound = 0.5F;
        float upperBound = 2F;
        float threshold = level.getRandom().nextFloat() * (upperBound-lowerBound) + lowerBound;
        return new WeightQuest(level, threshold);
    }
}
