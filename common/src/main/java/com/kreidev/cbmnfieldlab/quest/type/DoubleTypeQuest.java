package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class DoubleTypeQuest extends Quest {

    public final ElementalType firstType;
    public final ElementalType secondType;

    public DoubleTypeQuest(ServerLevel level, ElementalType firstType, ElementalType secondType) {
        super(Type.DOUBLE_TYPE, level);
        this.firstType = firstType;
        this.secondType = secondType;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
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

    @Override
    public QuestType<?> getType() {
        return QuestTypes.NATURE;
    }


    public static Quest createRandom(ServerLevel level) {
        List<ElementalType> types = ElementalTypes.INSTANCE.all();
        ElementalType fType = types.get(level.getRandom().nextInt(types.size()));
        ElementalType sType = types.get(level.getRandom().nextInt(types.size()));
        return new DoubleTypeQuest(level, fType, sType);
    }
}
