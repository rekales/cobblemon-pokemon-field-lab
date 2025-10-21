package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

// TODO: actually, maybe you could do some comparative quest using and abstract subclass that uses generics
// NOTE: scratch comment above, each would need their own nbt serializers anyway.
public abstract class Quest {
    public enum Type {
        SINGLE_TYPE("single_type", TypeQuest::createRandom),
        DOUBLE_TYPE("double_type", DoubleTypeQuest::createRandom),
//        DEX_NAME("dex_name", DexNameQuest::createRandom),
        ABILITY("ability", AbilityQuest::createRandom),
        MOVE("move", MoveQuest::createRandom),
        NATURE("nature", NatureQuest::createRandom),
//        EVO_STAGE("evo_stage", TypeQuest::createRandom),  // TODO
//        BIOME("spawn_biome", TypeQuest::createRandom),  // TODO
        STATS_TOTAL("stats_total", StatQuest::createRandom),
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

    public final long timeStamp;  // Timestamp ticks for when the quest started
    public final Type type;

    public Quest(Type type, ServerLevel level) {
        this.type = type;
        this.timeStamp = level.getGameTime();
    }

    protected Quest(Type type, long timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Quest{" + "timeStamp=" + timeStamp + ", type=" + type + '}';
    }

    public abstract boolean isEligible(Pokemon pokemon);

    // TODO: Maybe use codecs for these
    public static CompoundTag save(CompoundTag tag, Quest quest) {
        tag.putLong("Timestamp", quest.timeStamp);
        tag.putString("QuestType", quest.type.getKey());

        switch (quest.getType()) {
            case SINGLE_TYPE :
                TypeQuest typeQuest = (TypeQuest) quest;
                tag.putString("ElementalType", typeQuest.elementalType.getName());
                break;
            case ABILITY:
                AbilityQuest abilityQuest = (AbilityQuest) quest;
                tag.putString("AbilityName", abilityQuest.ability.getName());
                break;
            case NATURE:
                NatureQuest natureQuest = (NatureQuest) quest;
                tag.putString("NatureName", natureQuest.nature.getName().toString());
                break;
        }

        return tag;
    }

    public static @Nullable Quest load(CompoundTag tag) {
        long timestamp = tag.getLong("Timestamp");
        Type type = Type.fromKey(tag.getString("QuestType"));

        switch (type) {
            case SINGLE_TYPE :
                String typeName = tag.getString("ElementalType");
                ElementalType eType = ElementalTypes.INSTANCE.get(typeName);
                if (eType == null) return null;
                return new TypeQuest(timestamp, eType);
            case ABILITY:
                String abilityName = tag.getString("AbilityName");
                AbilityTemplate ability = Abilities.INSTANCE.get(abilityName);
                if (ability == null) return null;
                return new AbilityQuest(timestamp, ability);
            case NATURE:
                String natureResName = tag.getString("NatureName");
                ResourceLocation natureRes = ResourceLocation.tryParse(natureResName);
                if (natureRes == null) return null;
                Nature nature = Natures.INSTANCE.getNature(natureRes);
                if (nature == null) return null;
                return new NatureQuest(timestamp, nature);
            case null, default:
                return null;
        }
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
