package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
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

public class EvoQuest extends Quest {

    public static final MapCodec<EvoQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            Codec.INT.fieldOf("evolution_stage").forGetter(EvoQuest::getStage)
    ).apply(instance, EvoQuest::new));

    // 0 == unevolved
    public final int stage;

    // Random Quest
    public EvoQuest(ServerLevel level) {
        super(level);
        this.stage = level.getRandom().nextInt(1,2);
    }

    @SuppressWarnings("unused")
    public EvoQuest(ServerLevel level, int stage) {
        super(level, CommonConfig.evoQuestDifficulty);
        this.stage = stage;
    }

    public EvoQuest(long timestamp, ItemStack reward, int stage) {
        super(timestamp, reward);
        this.stage = stage;
    }

    @Override
    public boolean isEligible(@NotNull Pokemon pokemon) {
        return getEvoStage(pokemon.getSpecies()) >= this.stage;
    }

    @Override
    public String getModifierString() {
        return toBasicOrdinal(this.stage+1);
    }

    public int getStage() {
        return stage;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.EVO_STAGE;
    }


    public static int getEvoStage(Species species) {
        return getEvoStage(species, 0);
    }

    private static int getEvoStage(Species species, int stage) {
        if (species.getPreEvolution() == null) {
            return stage;
        } else {
            return getEvoStage(species.getPreEvolution().getSpecies(), stage+1);
        }
    }

    public static String toBasicOrdinal(int number) {
        if (number >= 11 && number <= 13) return number + "th";

        return switch(number%10) {
            case 1 -> number + "st";
            case 2 -> number + "nd";
            case 3 -> number + "rd";
            default -> number + "th";
        };
    }
}