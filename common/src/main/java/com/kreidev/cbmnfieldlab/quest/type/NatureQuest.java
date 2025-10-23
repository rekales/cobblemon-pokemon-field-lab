package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class NatureQuest extends Quest {

    public final Nature nature;

    public NatureQuest(ServerLevel level, Nature nature) {
        super(Type.NATURE, level);
        this.nature = nature;
    }

    public NatureQuest(long timestamp, Nature nature) {
        super(Type.NATURE, timestamp);
        this.nature = nature;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getNature() == this.nature;
    }

    @Override
    public String getModifierString() {
        return Component.translatable(this.nature.getDisplayName()).getString();
    }

    public static Quest createRandom(ServerLevel level) {
        Nature nature = Natures.INSTANCE.getRandomNature();
        return new NatureQuest(level, nature);
    }
}
