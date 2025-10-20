package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class TypeQuest extends Quest {

    public final ElementalType elementalType;

    public TypeQuest(ServerLevel level, ElementalType elementalType) {
        super(Type.SINGLE_TYPE, level);
        this.elementalType = elementalType;
    }


    @Override
    public boolean isEligible(Pokemon pokemon) {
        for (ElementalType eType : pokemon.getTypes()) {
            if (this.elementalType == eType) return true;
        }
        return false;
    }

    // NOTE: maybe use a constructor instead?
    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of ElementalTypes
//        ElementalType eType = null;
        return new TypeQuest(level, null);
    }
}
