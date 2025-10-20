package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Function;

// TODO: actually, maybe you could do some comparative quest using and abstract subclass that uses generics
// NOTE: scratch comment above, each would need their own nbt serializers anyway.
public abstract class Quest {
    public enum Type {
        SINGLE_TYPE("single_type", TypeQuest::createRandom),
        DOUBLE_TYPE("double_type", DoubleTypeQuest::createRandom),
        DEX_NAME("dex_name", DexNameQuest::createRandom),
        ABILITY("ability", AbilityQuest::createRandom),
        MOVE("move", MoveQuest::createRandom),
        NATURE("nature", NatureQuest::createRandom),
        EVO_STAGE("evo_stage", TypeQuest::createRandom),  // TODO
        BIOME("spawn_biome", TypeQuest::createRandom),  // TODO
        STATS_TOTAL("stats_total", TypeQuest::createRandom),  // TODO
        REGION("region", TypeQuest::createRandom),  // TODO
        SIZE("size", SizeQuest::createRandom),
        WEIGHT("weight", WeightQuest::createRandom);

        private final Function<ServerLevel, Quest> randomQuestFactory;
        private final String key;

        Type(String key, Function<ServerLevel, Quest> randomQuestFactory) {
            this.key = key;
            this.randomQuestFactory = randomQuestFactory;
        }

        public Function<ServerLevel, Quest> getFactory() {
            return this.randomQuestFactory;
        }

        public Quest getRandomQuest(ServerLevel level) {
            return this.randomQuestFactory.apply(level);
        }

        public String getKey() {
            return this.key;
        }
    }

    // Timestamp ticks for when the quest started
    public final long timeStamp;
    public final Type type;

    public Quest(Type type, ServerLevel level) {
        this.type = type;
        this.timeStamp = level.getGameTime();
    }

    public abstract boolean isEligible(Pokemon pokemon);

    // TODO: consider making a codec
    public CompoundTag save(CompoundTag tag) {
        return tag;
    }

    public Quest load(CompoundTag tag) {
        return null;
    }

    // TODO: implementation
    // TODO: configs
    // NOTE: The ServerLevel is really just to get the timestamp
    public static Quest getRandomQuest(ServerLevel level) {
        return null;
    }
}
