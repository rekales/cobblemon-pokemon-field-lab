package com.kreidev.cbmnfieldlab;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class FieldLabBlockEntity extends BlockEntity {

    private UUID currentUser = null;

    public FieldLabBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PokemonFieldLab.FIELD_LAB_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public void setUser(UUID user) {
        this.currentUser = user;
    }

    public boolean hasUser() {
        return this.currentUser != null;
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FieldLabBlockEntity be) {
        if (level.getGameTime() % 20 == 1 && !level.isClientSide()) {
            PokemonFieldLab.LOGGER.info(be.hasUser()+"");
        }
    }

}
