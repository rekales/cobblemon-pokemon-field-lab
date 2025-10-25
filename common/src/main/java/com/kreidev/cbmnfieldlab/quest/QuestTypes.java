package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.quest.type.AbilityQuest;
import com.kreidev.cbmnfieldlab.quest.type.NatureQuest;
import com.kreidev.cbmnfieldlab.quest.type.TypeQuest;
import net.minecraft.core.Registry;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class QuestTypes {

    public static final QuestType<AbilityQuest> ABILITY = register("ability", new QuestType<>(AbilityQuest.CODEC));
    public static final QuestType<TypeQuest> SINGLE_TYPE = register("1_type", new QuestType<>(TypeQuest.CODEC));
    public static final QuestType<NatureQuest> NATURE = register("nature", new QuestType<>(NatureQuest.CODEC));


    public static <T extends Quest> QuestType<T> register(String id, QuestType<T> questType) {
        return Registry.register(QuestType.REGISTRY, resLoc(id), questType);
    }
}
