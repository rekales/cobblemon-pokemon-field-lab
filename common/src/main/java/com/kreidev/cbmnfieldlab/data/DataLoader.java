package com.kreidev.cbmnfieldlab.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kreidev.cbmnfieldlab.quest.QuestRewardEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.List;
import java.util.Map;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

public class DataLoader extends SimpleJsonResourceReloadListener {

    public static final Codec<List<QuestRewardEntry>> LIST_CODEC = QuestRewardEntry.CODEC.codec().listOf();

    private static final Gson GSON = new Gson();
    public static final DataLoader INSTANCE = new DataLoader();

    public DataLoader() {
        super(GSON, "quest_reward");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        DataManager.clear();

        // TODO: check file names "individual_quest_reward", "minor_quest_reward", "major_quest_reward"

        object.forEach((id, json) -> {
            try {
                List<QuestRewardEntry> rewards = LIST_CODEC.parse(JsonOps.INSTANCE, json)
                        .getOrThrow();
                DataManager.addQuestRewards(rewards);
                LOGGER.info("Loaded {} quest rewards from {}", rewards.size(), id);
            } catch (Exception e) {
                LOGGER.error("Failed to load quest rewards: " + id);
                throw new RuntimeException("Failed to load quest rewards: " + id, e);
            }
        });
    }

    @ExpectPlatform
    public static DataLoader getInstance() {
        throw new AssertionError();
    }
}
