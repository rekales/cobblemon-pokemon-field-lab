package com.kreidev.cbmnfieldlab.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kreidev.cbmnfieldlab.quest.QuestRewardEntry;
import com.kreidev.cbmnfieldlab.quest.RewardManager;
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
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

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

        object.forEach((id, json) -> {
            try {
                if (id.equals(resLoc("individual_quest_rewards"))) {
                    List<QuestRewardEntry> rewards = LIST_CODEC.parse(JsonOps.INSTANCE, json)
                            .getOrThrow();
                    RewardManager.addIndivRewards(rewards);
                    LOGGER.info("Loaded individual quest rewards");
                } else if (id.equals(resLoc("minor_quest_rewards"))) {
                    List<QuestRewardEntry> rewards = LIST_CODEC.parse(JsonOps.INSTANCE, json)
                            .getOrThrow();
                    RewardManager.addMinorRewards(rewards);
                    LOGGER.info("Loaded minor quest rewards");
                } else if (id.equals(resLoc("major_quest_rewards"))) {
                    List<QuestRewardEntry> rewards = LIST_CODEC.parse(JsonOps.INSTANCE, json)
                            .getOrThrow();
                    RewardManager.addMajorRewards(rewards);
                    LOGGER.info("Loaded major quest rewards");
                }
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
