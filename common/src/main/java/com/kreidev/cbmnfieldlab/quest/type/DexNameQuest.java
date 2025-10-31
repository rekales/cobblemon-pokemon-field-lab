package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DexNameQuest extends Quest {

    public static final MapCodec<DexNameQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.STRING.fieldOf("quest_ability").forGetter(DexNameQuest::getName)
    ).apply(instance, DexNameQuest::new));

    public final String name;

    // Random Quest
    public DexNameQuest(ServerLevel level) {
        super(level, CommonConfig.dexNameQuestDifficulty);
        this.name = PokemonSpecies.INSTANCE.random().getName();
    }

    @SuppressWarnings("unused")
    public DexNameQuest(ServerLevel level, String name) {
        super(level);
        this.name = name;
    }

    public DexNameQuest(long timestamp, ItemStack reward, String name) {
        super(timestamp, reward);
        this.name = name;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        return PokemonSpecies.INSTANCE.getByName(this.name.toLowerCase()) == pokemon.getSpecies();
    }

    @Override
    public String getModifierString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.DEX_NAME;
    }
}
