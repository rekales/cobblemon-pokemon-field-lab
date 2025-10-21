package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    public CompoundTag save(CompoundTag tag) {
        tag.putLong("Timestamp", timeStamp);
        tag.putString("NatureName", nature.getName().toString());
        return tag;
    }

    public @Nullable Quest load(CompoundTag tag) {
        long timestamp = tag.getLong("Timestamp");
        String natureResName = tag.getString("NatureName");
        ResourceLocation natureRes = ResourceLocation.tryParse(natureResName);
        if (natureRes == null) return null;
        Nature nature = Natures.INSTANCE.getNature(natureRes);
        if (nature == null) return null;
        return new NatureQuest(timestamp, nature);
    }

    public static Quest createRandom(ServerLevel level) {
        Nature nature = Natures.INSTANCE.getRandomNature();
        return new NatureQuest(level, nature);
    }
}
