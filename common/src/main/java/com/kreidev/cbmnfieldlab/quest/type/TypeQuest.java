package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class TypeQuest extends Quest {

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

    public static Quest createRandom(ServerLevel level) {
        List<ElementalType> types = ElementalTypes.INSTANCE.all();
        ElementalType type = types.get(level.getRandom().nextInt(types.size()));
        return new TypeQuest(level, type);
    }
}
