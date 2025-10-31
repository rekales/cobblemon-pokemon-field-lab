package com.kreidev.cbmnfieldlab.quest;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConditionManager {
    private static final List<Pair<ResourceLocation, Float>> biomeConditions = new ArrayList<>();

    public static void addBiomeConditions(Map<ResourceLocation, Float> conditions) {
        conditions.forEach(((resourceLocation, difficulty) ->
                biomeConditions.add(new Pair<>(resourceLocation, difficulty))));
    }

    public static void clearAll() {
        biomeConditions.clear();
    }

    public static Pair<ResourceLocation, Float> getRandomBiomeCondition(ServerLevel level) {
        Registry<Biome> registry = level.registryAccess().registryOrThrow(Registries.BIOME);

        while (!biomeConditions.isEmpty()) {
            Pair<ResourceLocation, Float> rand = biomeConditions.get(level.getRandom().nextInt(biomeConditions.size()));
            if (registry.containsKey(rand.getFirst())) return rand;
            else biomeConditions.remove(rand);
        }

        // Fallback
        return new Pair<>(registry.getKey(registry.getRandom(level.getRandom()).orElseThrow().value()), 1F);
    }
}
