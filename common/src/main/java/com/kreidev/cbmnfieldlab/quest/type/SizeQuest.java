package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SizeQuest extends Quest {

    public static final MapCodec<SizeQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.FLOAT.fieldOf("quest_size").forGetter(SizeQuest::getSize)
    ).apply(instance, SizeQuest::new));

    public final float size;

    // Random Quest
    public SizeQuest(ServerLevel level) {
        super(level);

        float lowerBound = CommonConfig.sizeQuestLowerBound;
        float upperBound = CommonConfig.sizeQuestUpperBound;
        this.size = level.getRandom().nextFloat() * (upperBound-lowerBound) + lowerBound;;
    }

    public SizeQuest(ServerLevel level, float size) {
        super(level);
        this.size = size;
    }

    public SizeQuest(long timestamp, ItemStack reward, float size) {
        super(timestamp, reward);
        this.size = size;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        // NOTE: didn't find any "size" attribute but I did find height
        return pokemon.getForm().getHeight() < this.size;
    }

    @Override
    public String getModifierString() {
        return String.format("%.2f", this.size);
    }

    public float getSize() {
        return size;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.SIZE;
    }
}
