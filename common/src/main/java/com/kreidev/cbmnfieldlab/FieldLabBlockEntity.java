package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FieldLabBlockEntity extends BlockEntity {

    @Nullable private UUID currentUser = null;

    public FieldLabBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PokemonFieldLab.FIELD_LAB_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public void setUser(@Nullable UUID user) {
         this.currentUser = user;
    }

    public @Nullable UUID getUser() {
        return this.currentUser;
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FieldLabBlockEntity be) {
        if (level.getGameTime() % 5 == 1 && !level.isClientSide()) {
            // Check if there's a player using
            if (be.getUser() != null && level.getPlayerByUUID(be.getUser()) instanceof ServerPlayer player) {
                if (!(player.containerMenu instanceof FieldLabMenu)) be.setUser(null);
            }

            // Update blockstate accordingly
            boolean newState = be.getUser()!=null;
            boolean currentState = blockState.getValue(FieldLabBlock.OPEN);
            if (currentState!=newState) {
                level.setBlockAndUpdate(blockPos, blockState.setValue(FieldLabBlock.OPEN, newState));
            }
        }
    }
}
