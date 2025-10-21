package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AbilityQuest extends Quest {

    public final AbilityTemplate ability;

//    public AbilityQuest(ServerLevel level, Ability ability) {
//        this(level, ability.getTemplate());
//    }

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

    public CompoundTag save(CompoundTag tag) {
        tag.putLong("Timestamp", timeStamp);
        tag.putString("AbilityName", ability.getName());
        return tag;
    }

    public @Nullable Quest load(CompoundTag tag) {
        long timestamp = tag.getLong("Timestamp");
        String abilityName = tag.getString("AbilityName");
        AbilityTemplate ability = Abilities.INSTANCE.get(abilityName);
        if (ability == null) return null;
        return new AbilityQuest(timestamp, ability);
    }

    public static Quest createRandom(ServerLevel level) {
        List<AbilityTemplate> abilities = Abilities.INSTANCE.all();
        AbilityTemplate ability = abilities.get(level.getRandom().nextInt(abilities.size()));
        return new AbilityQuest(level, ability);
    }
}
