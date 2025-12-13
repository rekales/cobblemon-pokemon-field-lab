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

// Not to be confused with QuestType
public class TypeQuest extends Quest {

    public static final MapCodec<TypeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            ElementalType.getBY_STRING_CODEC().fieldOf("quest_elemental_type").forGetter(TypeQuest::getElementalType)
    ).apply(instance, TypeQuest::new));

    public final ElementalType elementalType;

    // Random Quest
    public TypeQuest(ServerLevel level) {
        super(level, CommonConfig.singleTypeQuestDifficulty);

        Set<ElementalType> typeSet = new HashSet<>();
        for (Species specie : PokemonSpecies.getSpecies()) {
            typeSet.add(specie.getPrimaryType());
            if (specie.getSecondaryType() != null) {
                typeSet.add(specie.getSecondaryType());
            }
        }

        // TODO: cache
        List<ElementalType> types = new ArrayList<>(typeSet);
        this.elementalType = types.get(level.getRandom().nextInt(types.size()));
    }

    @SuppressWarnings("unused")
    public TypeQuest(ServerLevel level, ElementalType elementalType) {
        super(level);
        this.elementalType = elementalType;
    }

    public TypeQuest(long timestamp, ItemStack reward, ElementalType elementalType) {
        super(timestamp, reward);
        this.elementalType = elementalType;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        for (ElementalType eType : pokemon.getTypes()) {
            if (this.elementalType == eType) return true;
        }
        return false;
    }

    @Override
    public String getModifierString() {
        return this.elementalType.getDisplayName().getString();
    }

    public ElementalType getElementalType() {
        return elementalType;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.SINGLE_TYPE;
    }
}
