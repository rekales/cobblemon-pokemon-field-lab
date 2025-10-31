package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokedex.Dexes;
import com.cobblemon.mod.common.api.pokedex.def.PokedexDef;
import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RegionQuest extends Quest {

    public static final MapCodec<RegionQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            ResourceLocation.CODEC.fieldOf("region").forGetter(RegionQuest::getRegion)
    ).apply(instance, RegionQuest::new));

    public final ResourceLocation region;

    // Random Quest
    public RegionQuest(ServerLevel level) {
        super(level, CommonConfig.regionQuestDifficulty);
        List<ResourceLocation> dexes = new ArrayList<>(Dexes.INSTANCE.getDexEntryMap().keySet());
        this.region = dexes.get(level.getRandom().nextInt(dexes.size()));
    }

    @SuppressWarnings("unused")
    public RegionQuest(ServerLevel level, ResourceLocation region) {
        super(level);
        this.region = region;
    }

    public RegionQuest(long timestamp, ItemStack reward, ResourceLocation region) {
        super(timestamp, reward);
        this.region = region;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        PokedexDef dex = Dexes.INSTANCE.getDexEntryMap().get(this.region);
        if (dex == null) return false;

        for (PokedexEntry entry : dex.getEntries()) {
            if (PokemonSpecies.INSTANCE.getByIdentifier(entry.getSpeciesId()) == pokemon.getSpecies()) return true;
        }
        return false;
    }

    @Override
    public String getModifierString() {
        String str = this.region.getPath();  // TODO: replace with localized name
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public ResourceLocation getRegion() {
        return region;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.REGION;
    }
}
