package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
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

    public CompoundTag save(CompoundTag tag) {

        return tag;
    }

    public Quest load(CompoundTag tag) {
        return null;
    }

    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all types of Abilities
//        Ability eType = null;
        return new DexNameQuest(level, null);
    }
}
