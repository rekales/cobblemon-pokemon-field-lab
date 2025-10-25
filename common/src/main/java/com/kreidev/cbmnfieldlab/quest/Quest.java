package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.item.CobblemonItem;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.type.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

// TODO: actually, maybe you could do some comparative quest using and abstract subclass that uses generics
// NOTE: scratch comment above, each would need their own nbt serializers anyway.
@SuppressWarnings("unused")
public abstract class Quest {
    public enum Type {
        SINGLE_TYPE("1_type", TypeQuest::createRandom),
        DOUBLE_TYPE("2_types", DoubleTypeQuest::createRandom),
//        DEX_NAME("pokedex_name", DexNameQuest::createRandom),
        ABILITY("ability", AbilityQuest::createRandom),
        MOVE("move", MoveQuest::createRandom),
        NATURE("nature", NatureQuest::createRandom),
//        EVO_STAGE("evolution_stage", TypeQuest::createRandom),  // TODO
//        BIOME("from_biome", TypeQuest::createRandom),  // TODO
        STATS_TOTAL("base_stats_total", StatQuest::createRandom),
//        REGION("region", RegionQuest::createRandom),
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

        public static @Nullable Type fromKey(String key) {
            for (Type type : Type.values()) {
                if (type.getKey().equals(key)) return type;
            }
            return null;
        }
    }

    public static final Codec<Quest> CODEC = QuestType.REGISTRY.byNameCodec()
            .dispatch("type", Quest::getType, QuestType::codec);

    public final long timeStamp;  // Timestamp ticks for when the quest started
    public final Type type;
    @NotNull public final ItemStack reward;

    public Quest(Type type, ServerLevel level) {
        this.type = type;
        this.timeStamp = level.getGameTime();
        this.reward = getReward();
    }

    // NOTE: In case there's a need to override reward
    public Quest(Type type, ServerLevel level, @NotNull ItemStack reward) {
        this.type = type;
        this.timeStamp = level.getGameTime();
        this.reward = reward;
    }

    // NOTE: for deserialization purposes
    protected Quest(Type type, long timeStamp, @NotNull ItemStack reward) {
        this.type = type;
        this.timeStamp = timeStamp;
        this.reward = reward;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "Quest{" + "timeStamp=" + timeStamp + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quest quest)) return false;
        // good enough for practical purposes
        return this.type == quest.type && this.timeStamp == quest.timeStamp;
    }

    public abstract boolean isEligible(Pokemon pokemon);

    public abstract String getModifierString();

//    public abstract float getDifficulty();

    public abstract QuestType<?> getType();

     public @NotNull ItemStack getReward() {
         // TODO: generate reward relative to getDifficulty()
         return new ItemStack(CobblemonItems.PROTECTOR);
     }

    // TODO: implementation
    // TODO: configs
    // NOTE: The ServerLevel is really just to get the timestamp
    public static Quest getRandomQuest(ServerLevel level) {
        int i = level.getRandom().nextInt(3);
        return switch (i) {
            case 0 -> Type.SINGLE_TYPE.getRandomQuest(level);
            case 1 -> Type.ABILITY.getRandomQuest(level);
            default -> Type.NATURE.getRandomQuest(level);
        };
    }
}
