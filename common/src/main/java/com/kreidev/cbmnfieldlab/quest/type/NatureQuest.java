package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.pokemon.Nature;
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

public class NatureQuest extends Quest {

    public static final MapCodec<NatureQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Nature.getBY_IDENTIFIER_CODEC().fieldOf("quest_nature").forGetter(NatureQuest::getNature)
    ).apply(instance, NatureQuest::new));

    public final Nature nature;

    // Random Quest
    public NatureQuest(ServerLevel level) {
        super(level, CommonConfig.natureQuestDifficulty);
        this.nature = Natures.INSTANCE.getRandomNature();
    }

    @SuppressWarnings("unused")
    public NatureQuest(ServerLevel level, Nature nature) {
        super(level);
        this.nature = nature;
    }

    public NatureQuest(long timestamp, ItemStack reward, Nature nature) {
        super(timestamp, reward);
        this.nature = nature;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        return pokemon.getNature() == this.nature;
    }

    @Override
    public String getModifierString() {
        return Component.translatable(this.nature.getDisplayName()).getString();
    }

    public Nature getNature() {
        return nature;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.NATURE;
    }
}
