package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class DoubleTypeQuest extends Quest {

    public final ElementalType firstType;
    public final ElementalType secondType;

    public DoubleTypeQuest(ServerLevel level, ElementalType firstType, ElementalType secondType) {
        super(Type.DOUBLE_TYPE, level);
        this.firstType = firstType;
        this.secondType = secondType;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        // It's janky-ass shit I know
        for (ElementalType eType : pokemon.getTypes()) {
            if (this.firstType == eType) {
                for (ElementalType fType : pokemon.getTypes()) {
                    if (this.secondType == fType) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of ElementalTypes
//        ElementalType eType = null;
        return new DoubleTypeQuest(level, null, null);
    }
}
