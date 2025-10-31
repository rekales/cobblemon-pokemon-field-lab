package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AbilityQuest extends Quest {

    public static final MapCodec<AbilityQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            AbilityTemplate.getCODEC().fieldOf("quest_ability").forGetter(AbilityQuest::getAbility)
    ).apply(instance, AbilityQuest::new));

    public final AbilityTemplate ability;

    // Random Quest
    public AbilityQuest(ServerLevel level) {
        super(level, CommonConfig.abilityQuestDifficulty);
        List<AbilityTemplate> abilities = Abilities.INSTANCE.all();
        this.ability = abilities.get(level.getRandom().nextInt(abilities.size()));
    }

    @SuppressWarnings("unused")
    public AbilityQuest(ServerLevel level, AbilityTemplate abilityTemplate) {
        super(level);
        this.ability = abilityTemplate;
    }

    public AbilityQuest(long timestamp, ItemStack reward, AbilityTemplate abilityTemplate) {
        super(timestamp, reward);
        this.ability = abilityTemplate;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
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
    public @NotNull QuestType<?> getType() {
        return QuestTypes.ABILITY;
    }
}
