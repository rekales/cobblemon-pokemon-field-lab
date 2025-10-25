package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class AbilityQuest extends Quest {

    public static final MapCodec<AbilityQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(AbilityQuest::getTimeStamp),
            AbilityTemplate.getCODEC().fieldOf("quest_ability").forGetter(AbilityQuest::getAbility)
    ).apply(instance, AbilityQuest::new));

    public final AbilityTemplate ability;

    public AbilityQuest(ServerLevel level, AbilityTemplate abilityTemplate) {
        super(Type.ABILITY, level);
        this.ability = abilityTemplate;
    }

    public AbilityQuest(long timestamp, AbilityTemplate abilityTemplate) {
        super(Type.ABILITY, timestamp, new ItemStack(Items.STICK));
        this.ability = abilityTemplate;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        return pokemon.getAbility().getTemplate() == this.ability;
    }

    @Override
    public String getModifierString() {
        return Component.translatable(this.ability.getDisplayName()).getString();
    }

    public AbilityTemplate getAbility() {
        return ability;
    }

    @Override
    public QuestType<?> getType() {
        return QuestTypes.ABILITY;
    }

    public static Quest createRandom(ServerLevel level) {
        List<AbilityTemplate> abilities = Abilities.INSTANCE.all();
        AbilityTemplate ability = abilities.get(level.getRandom().nextInt(abilities.size()));
        return new AbilityQuest(level, ability);
    }
}
