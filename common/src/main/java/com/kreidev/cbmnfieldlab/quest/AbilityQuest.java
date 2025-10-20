package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class AbilityQuest extends Quest {

    public final Ability ability;

    public AbilityQuest(ServerLevel level, Ability ability) {
        super(Type.ABILITY, level);
        this.ability = ability;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getAbility() == this.ability;  // TODO: check if a pokemon only has one ability
    }

    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of Abilities
//        Ability eType = null;
        return new AbilityQuest(level, null);
    }
}
