package com.kreidev.cbmnfieldlab.quest.type;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestType;
import com.kreidev.cbmnfieldlab.quest.QuestTypes;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class MoveQuest extends Quest {

    public final MoveTemplate move;

    public MoveQuest(ServerLevel level, MoveTemplate move) {
        super(Quest.Type.MOVE, level);
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

    @Override
    public QuestType<?> getType() {
        return QuestTypes.NATURE;
    }


    public static Quest createRandom(ServerLevel level) {
        List<MoveTemplate> moves = Moves.INSTANCE.all();
        MoveTemplate move = moves.get(level.getRandom().nextInt(moves.size()));
        return new MoveQuest(level, move);
    }
}
