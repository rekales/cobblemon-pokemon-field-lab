package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel;

public class MoveQuest extends Quest{

    public final Move move;

    public MoveQuest(ServerLevel level, Move move) {
        super(Quest.Type.MOVE, level);
        this.move = move;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        for (Move move : pokemon.getMoveSet().getMoves()) {
            if (this.move == move) return true;
        }
        return false;    }

    public static Quest createRandom(ServerLevel level) {
        // TODO: figure out how to get all moves
        return new MoveQuest(level, null);
    }
}
