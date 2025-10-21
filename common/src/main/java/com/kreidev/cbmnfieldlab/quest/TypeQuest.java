package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TypeQuest extends Quest {

    public final ElementalType elementalType;

    public TypeQuest(ServerLevel level, ElementalType elementalType) {
        super(Type.SINGLE_TYPE, level);
        this.elementalType = elementalType;
    }

    protected TypeQuest(long timestamp, ElementalType elementalType) {
        super(Type.SINGLE_TYPE, timestamp);
        this.elementalType = elementalType;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        for (ElementalType eType : pokemon.getTypes()) {
            if (this.elementalType == eType) return true;
        }
        return false;
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putLong("Timestamp", timeStamp);
        tag.putString("ElementalType", elementalType.getName());
        return tag;
    }

    public static @Nullable Quest load(CompoundTag tag) {
        long timestamp = tag.getLong("Timestamp");
        String typeName = tag.getString("ElementalType");
        ElementalType eType = ElementalTypes.INSTANCE.get(typeName);
        if (eType == null) return null;
        return new TypeQuest(timestamp, eType);
    }

    public static Quest createRandom(ServerLevel level) {
        List<ElementalType> types = ElementalTypes.INSTANCE.all();
        ElementalType type = types.get(level.getRandom().nextInt(types.size()));
        return new TypeQuest(level, type);
    }
}
