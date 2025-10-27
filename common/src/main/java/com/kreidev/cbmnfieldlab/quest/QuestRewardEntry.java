package com.kreidev.cbmnfieldlab.quest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

// TODO: could be grouped with minor and major quest reward loot pool in a separate reward manager class.
public record QuestRewardEntry(Item item, int minCount, int maxCount, float cost) {

    public static final MapCodec<QuestRewardEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(QuestRewardEntry::item),
            Codec.INT.fieldOf("min_count").forGetter(QuestRewardEntry::minCount),
            Codec.INT.fieldOf("max_count").forGetter(QuestRewardEntry::maxCount),
            Codec.FLOAT.fieldOf("cost").forGetter(QuestRewardEntry::cost)
    ).apply(instance, QuestRewardEntry::new));
}