package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
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

    public static Quest createRandom(ServerLevel level) {
        // TODO: config for weight range
        return new WeightQuest(level, 0);
    }
}
