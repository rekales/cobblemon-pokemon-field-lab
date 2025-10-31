package com.kreidev.cbmnfieldlab.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kreidev.cbmnfieldlab.quest.ConditionManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class ConditionDataLoader extends SimpleJsonResourceReloadListener {

    public static final Codec<Map<ResourceLocation, Float>> CODEC = Codec.unboundedMap(ResourceLocation.CODEC, Codec.FLOAT);

    private static final Gson GSON = new Gson();
    public static final ConditionDataLoader INSTANCE = new ConditionDataLoader();

    public ConditionDataLoader() {
        super(GSON, "quest_condition");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ConditionManager.clearAll();

        // TODO: fuck it, do runtime verification for now
        object.forEach((id, json) -> {
            try {
                if (id.equals(resLoc("biome_quest"))) {
                    Map<ResourceLocation, Float> conditions = CODEC.parse(JsonOps.INSTANCE, json)
                            .getOrThrow();
                    ConditionManager.addBiomeConditions(conditions);
                    LOGGER.info("Loaded biome quest conditions");
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load biome quest conditions: " + id);
                throw new RuntimeException("Failed to load biome quest conditions: " + id, e);
            }
        });
    }

    @ExpectPlatform
    public static ConditionDataLoader getInstance() {
        throw new AssertionError();
    }
}
