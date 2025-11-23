package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.ConditionManager;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BiomeQuest extends Quest {

    public static final MapCodec<BiomeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            ResourceLocation.CODEC.fieldOf("biome_res").forGetter(BiomeQuest::getBiomeRes),
            Species.getBY_IDENTIFIER_CODEC().listOf().fieldOf("precomp_species").forGetter(BiomeQuest::getPreCompList)
    ).apply(instance, BiomeQuest::new));

    public final ResourceLocation biomeRes;
    public final List<Species> preCompList;

    // Random Quest
    public BiomeQuest(ServerLevel level) {
        this(level, ConditionManager.getRandomBiomeCondition(level));
    }

    private BiomeQuest(ServerLevel level, Pair<ResourceLocation, Float> pair) {
        super(level, CommonConfig.biomeQuestDifficulty*pair.getSecond());

        Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);
        Biome biome = registry.get(pair.getFirst());
        this.biomeRes = pair.getFirst();
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
                .map(PokemonSpecies::getByName)
                .toList();
    }

    @SuppressWarnings("unused")
    public BiomeQuest(ServerLevel level, ResourceLocation biomeRes, List<Species> preCompList) {
        super(level);
        this.biomeRes = biomeRes;
        this.preCompList = preCompList;
    }

    public BiomeQuest(long timestamp, ItemStack reward, ResourceLocation biomeRes, List<Species> preCompList) {
        super(timestamp, reward);
        this.biomeRes = biomeRes;
        this.preCompList = preCompList;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        return preCompList.contains(pokemon.getSpecies());
    }

    @Override
    public String getModifierString() {
        return this.biomeRes.getPath();
    }

    public ResourceLocation getBiomeRes() {
        return this.biomeRes;
    }

    public List<Species> getPreCompList() {
        return preCompList;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.BIOME;
    }
}