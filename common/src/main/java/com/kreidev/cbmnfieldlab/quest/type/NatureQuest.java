package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Nature;
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

public class NatureQuest extends Quest {

    public static final MapCodec<NatureQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(NatureQuest::getTimeStamp),
            Nature.getBY_IDENTIFIER_CODEC().fieldOf("quest_nature").forGetter(NatureQuest::getNature)
    ).apply(instance, NatureQuest::new));

    public final Nature nature;

    public NatureQuest(ServerLevel level, Nature nature) {
        super(Type.NATURE, level);
        this.nature = nature;
    }

    public NatureQuest(long timestamp, Nature nature) {
        super(Type.NATURE, timestamp, new ItemStack(Items.STICK));
        this.nature = nature;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
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
    public QuestType<?> getType() {
        return QuestTypes.NATURE;
    }

    public static Quest createRandom(ServerLevel level) {
        Nature nature = Natures.INSTANCE.getRandomNature();
        return new NatureQuest(level, nature);
    }
}
