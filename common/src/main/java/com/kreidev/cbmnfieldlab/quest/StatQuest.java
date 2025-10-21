package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.api.pokemon.stats.Stat;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;

public class StatQuest extends Quest {

    public final int statTotal;

    public StatQuest(ServerLevel level, int statTotal) {
        super(Type.STATS_TOTAL, level);
        this.statTotal = statTotal;
    }

    @Override
    public boolean isEligible(Pokemon pokemon) {
        int stats = 0;
        Map<Stat, Integer> baseStats = pokemon.getSpecies().getBaseStats();
        stats += baseStats.get(Stats.HP);
        stats += baseStats.get(Stats.ATTACK);
        stats += baseStats.get(Stats.DEFENCE);
        stats += baseStats.get(Stats.SPECIAL_ATTACK);
        stats += baseStats.get(Stats.SPECIAL_DEFENCE);
        stats += baseStats.get(Stats.SPEED);
        return stats < this.statTotal;
    }

    public CompoundTag save(CompoundTag tag) {

        return tag;
    }

    public Quest load(CompoundTag tag) {
        return null;
    }

    public static Quest createRandom(ServerLevel level) {
        int lowerBound = 200;
        int upperBound = 400;
        int threshold = level.getRandom().nextInt(lowerBound, upperBound);
        return new StatQuest(level, threshold);
    }
}
