package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class AbilityQuest extends Quest {

    public final AbilityTemplate ability;

    public AbilityQuest(ServerLevel level, AbilityTemplate abilityTemplate) {
        super(Type.ABILITY, level);
        this.ability = abilityTemplate;
    }

    protected AbilityQuest(long timestamp, AbilityTemplate abilityTemplate) {
        super(Type.ABILITY, timestamp);
        this.ability = abilityTemplate;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getAbility().getTemplate() == this.ability;
    }

    public static Quest createRandom(ServerLevel level) {
        List<AbilityTemplate> abilities = Abilities.INSTANCE.all();
        AbilityTemplate ability = abilities.get(level.getRandom().nextInt(abilities.size()));
        return new AbilityQuest(level, ability);
    }
}
