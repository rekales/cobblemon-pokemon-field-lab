package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoveQuest extends Quest {

    public static final MapCodec<MoveQuest> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.LONG.fieldOf("timestamp").forGetter(Quest::getTimeStamp),
            ItemStack.CODEC.fieldOf("reward").forGetter(Quest::getReward),
            MoveTemplate.getBY_STRING_CODEC().fieldOf("quest_move").forGetter(MoveQuest::getMove)
    ).apply(instance, MoveQuest::new));

    public final MoveTemplate move;

    // Random Quest
    public MoveQuest(ServerLevel level) {
        super(level);
        List<MoveTemplate> moves = Moves.INSTANCE.all();
        this.move = moves.get(level.getRandom().nextInt(moves.size()));
    }

    public MoveQuest(ServerLevel level, MoveTemplate move) {
        super(level);
        this.move = move;
    }

    public MoveQuest(long timestamp, ItemStack reward, MoveTemplate move) {
        super(timestamp, reward);
        this.move = move;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        for (Move move : pokemon.getMoveSet().getMoves()) {
            if (this.move == move.getTemplate()) return true;
        }
        return false;
    }

    @Override
    public String getModifierString() {
        return this.move.getDisplayName().getString();
    }

    public MoveTemplate getMove() {
        return move;
    }

    @Override
    public @NotNull QuestType<?> getType() {
        return QuestTypes.MOVE;
    }
}
