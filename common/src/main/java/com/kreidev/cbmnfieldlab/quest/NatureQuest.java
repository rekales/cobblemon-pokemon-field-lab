package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class NatureQuest extends Quest {

    public final Nature nature;

    public NatureQuest(ServerLevel level, Nature nature) {
        super(Type.NATURE, level);
        this.nature = nature;
    }

    protected NatureQuest(long timestamp, Nature nature) {
        super(Type.NATURE, timestamp);
        this.nature = nature;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getNature() == this.nature;
    }

    public static Quest createRandom(ServerLevel level) {
        Nature nature = Natures.INSTANCE.getRandomNature();
        return new NatureQuest(level, nature);
    }
}
