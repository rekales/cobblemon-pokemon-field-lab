package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokedex.Dexes;
import com.cobblemon.mod.common.api.pokedex.def.PokedexDef;
import com.cobblemon.mod.common.api.pokedex.entry.DexEntries;
import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

public class RegionQuest extends Quest {

    public final ResourceLocation region;

    public RegionQuest(ServerLevel level, ResourceLocation region) {
        super(level);
        this.region = region;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        PokedexEntry entry = DexEntries.INSTANCE.getEntries().get(pokemon.getSpecies().getResourceIdentifier());
        if (entry == null) return false;
        PokedexDef dex = Dexes.INSTANCE.getDexEntryMap().get(region);

        return true;
//        DexEntries.INSTANCE.
//        return entry;

        // TODO
    }

    @Override
    public String getModifierString() {
        return this.region.toString();  // TODO
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.NATURE;
    }


    public static Quest createRandom(ServerLevel level) {
        // TODO
        return new WeightQuest(level);
    }
}
