package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        super(level);
        List<ElementalType> types = ElementalTypes.INSTANCE.all();
        this.firstType = types.get(level.getRandom().nextInt(types.size()));
        this.secondType = types.get(level.getRandom().nextInt(types.size()));
    }

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
        // It's janky-ass shit I know
        for (ElementalType eType : pokemon.getTypes()) {
            if (this.firstType == eType) {
                for (ElementalType fType : pokemon.getTypes()) {
                    if (this.secondType == fType) {
                        return true;
                    }
                }
            }
        }
        return false;
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
