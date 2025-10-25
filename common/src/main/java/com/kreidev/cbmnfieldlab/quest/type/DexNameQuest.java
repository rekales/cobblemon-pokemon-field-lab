package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import net.minecraft.server.level.ServerLevel;

public class DexNameQuest extends Quest {

    public final String name;

    public DexNameQuest(ServerLevel level, String name) {
        super(Type.SINGLE_TYPE, level);
        this.name = name;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getDisplayName().toString() == this.name;  // TODO: find a way to get the pokedex entry name
    }

    @Override
    public String getModifierString() {
        return this.name;
    }

    @Override
    public QuestType<?> getType() {
        return QuestTypes.NATURE;
    }


    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of Abilities
//        Ability eType = null;
        return new DexNameQuest(level, null);
    }
}
