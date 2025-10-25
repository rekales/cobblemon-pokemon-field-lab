package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
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
import net.minecraft.world.item.Items;

import java.util.List;

public class TypeQuest extends Quest {

    public static final MapCodec<TypeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(TypeQuest::getTimeStamp),
            ElementalType.getBY_STRING_CODEC().fieldOf("quest_elemental_type").forGetter(TypeQuest::getElementalType)
    ).apply(instance, TypeQuest::new));

    public final ElementalType elementalType;

    public TypeQuest(ServerLevel level, ElementalType elementalType) {
        super(Type.SINGLE_TYPE, level);
        this.elementalType = elementalType;
    }

    public TypeQuest(long timestamp, ElementalType elementalType) {
        super(Type.SINGLE_TYPE, timestamp, new ItemStack(Items.STICK));
        this.elementalType = elementalType;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
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
    public QuestType<?> getType() {
        return QuestTypes.SINGLE_TYPE;
    }


    public static Quest createRandom(ServerLevel level) {
        List<ElementalType> types = ElementalTypes.INSTANCE.all();
        ElementalType type = types.get(level.getRandom().nextInt(types.size()));
        return new TypeQuest(level, type);
    }
}
