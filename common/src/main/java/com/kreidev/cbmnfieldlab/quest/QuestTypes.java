package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.quest.type.AbilityQuest;
import com.kreidev.cbmnfieldlab.quest.type.NatureQuest;
import com.kreidev.cbmnfieldlab.quest.type.TypeQuest;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class QuestTypes {

    public static final QuestType<AbilityQuest> ABILITY = register("ability", AbilityQuest.CODEC);
    public static final QuestType<TypeQuest> SINGLE_TYPE = register("1_type", TypeQuest.CODEC);
    public static final QuestType<NatureQuest> NATURE = register("nature", NatureQuest.CODEC);

    public static <T extends Quest> QuestType<T> register(String id, MapCodec<T> codec) {

        return Registry.register(QuestType.REGISTRY, resLoc(id), new QuestType<>(id, codec));
    }
}
