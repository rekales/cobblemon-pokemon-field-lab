package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BiomeQuest extends Quest {

    public static final MapCodec<BiomeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.STRING.fieldOf("biome_key").forGetter(BiomeQuest::getBiomeKey),
            Species.getBY_IDENTIFIER_CODEC().listOf().fieldOf("precomp_species").forGetter(BiomeQuest::getPreCompList)
    ).apply(instance, BiomeQuest::new));

    public final String biomeKey;
    public final List<Species> preCompList;

    // Random Quest
    public BiomeQuest(ServerLevel level) {
        super(level, CommonConfig.biomeQuestDifficulty);
        Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
        ResourceKey<Biome> key = registry.getRandom(level.getRandom()).orElseThrow().key();
        Biome biome = registry.get(key);
        this.biomeKey = key.toString();
        this.preCompList = CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails().stream()
                .filter(spawnDetail-> {
                    for (SpawningCondition<?> cond : spawnDetail.getConditions()) {
                        if (cond.getBiomes() == null) continue;
                        for (RegistryLikeCondition<Biome> c : cond.getBiomes()) {
                            if (c.fits(biome, registry)) return true;
                        }
                    }
                    return false;
                }).map(spawnDetail -> {
                    String specie = ((PokemonSpawnDetail) spawnDetail).getPokemon().getSpecies();
                    if (specie == null) return null;
                    return specie.toLowerCase();
                }).filter(Objects::nonNull)
                .distinct()
                .map(PokemonSpecies.INSTANCE::getByName)
                .toList();
    }

    @SuppressWarnings("unused")
    public BiomeQuest(ServerLevel level, String biomeKey, List<Species> preCompList) {
        super(level);
        this.biomeKey = biomeKey;
        this.preCompList = preCompList;
    }

    public BiomeQuest(long timestamp, ItemStack reward, String biomeKey, List<Species> preCompList) {
        super(timestamp, reward);
        this.biomeKey = biomeKey;
        this.preCompList = preCompList;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        return preCompList.contains(pokemon.getSpecies());
    }

    @Override
    public String getModifierString() {
        return this.biomeKey;
    }

    public String getBiomeKey() {
        return this.biomeKey;
    }

    public List<Species> getPreCompList() {
        return preCompList;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.BIOME;
    }
}