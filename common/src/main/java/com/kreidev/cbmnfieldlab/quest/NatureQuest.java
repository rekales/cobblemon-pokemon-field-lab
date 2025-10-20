package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class NatureQuest extends Quest {

    public final Nature nature;

    public NatureQuest(ServerLevel level, Nature nature) {
        super(Type.NATURE, level);
        this.nature = nature;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getNature() == this.nature;
    }

    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of Natures
//        Ability eType = null;
        return new NatureQuest(level, null);
    }
}
