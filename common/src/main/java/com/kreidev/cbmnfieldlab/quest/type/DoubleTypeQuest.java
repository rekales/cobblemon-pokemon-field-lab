package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleTypeQuest extends Quest {

    public static final MapCodec<DoubleTypeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            ElementalType.getBY_STRING_CODEC().fieldOf("quest_first_type").forGetter(DoubleTypeQuest::getFirstType),
            ElementalType.getBY_STRING_CODEC().fieldOf("quest_second_type").forGetter(DoubleTypeQuest::getSecondType)
    ).apply(instance, DoubleTypeQuest::new));

    public final ElementalType firstType;
    public final ElementalType secondType;

    // Random Quest
    public DoubleTypeQuest(ServerLevel level) {
        super(level, CommonConfig.doubleTypeQuestDifficulty);

        Set<ElementalType> typeSet = new HashSet<>();
        for (Species specie : PokemonSpecies.getSpecies()) {
            typeSet.add(specie.getPrimaryType());
            if (specie.getSecondaryType() != null) {
                typeSet.add(specie.getSecondaryType());
            }
        }

        // TODO: cache
        List<ElementalType> types = new ArrayList<>(typeSet);
        this.firstType = types.get(level.getRandom().nextInt(types.size()));
        this.secondType = types.get(level.getRandom().nextInt(types.size()));
    }

    @SuppressWarnings("unused")
    public DoubleTypeQuest(ServerLevel level, ElementalType firstType, ElementalType secondType) {
        super(level);
        this.firstType = firstType;
        this.secondType = secondType;
    }

    public DoubleTypeQuest(long timestamp, ItemStack reward, ElementalType firstType, ElementalType secondType) {
        super(timestamp, reward);
        this.firstType = firstType;
        this.secondType = secondType;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        Species specie = pokemon.getSpecies();
        return (this.firstType == specie.getPrimaryType() && this.secondType == specie.getSecondaryType())
                || (this.secondType == specie.getPrimaryType() && this.firstType == specie.getSecondaryType());
    }

    @Override
    public String getModifierString() {
        return this.firstType.getDisplayName().getString() + " & " + this.secondType.getDisplayName().getString();
    }

    public ElementalType getFirstType() {
        return firstType;
    }

    public ElementalType getSecondType() {
        return secondType;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.DOUBLE_TYPE;
    }
}
