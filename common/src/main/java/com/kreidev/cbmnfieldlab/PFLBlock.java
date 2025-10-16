package com.kreidev.cbmnfieldlab;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class PFLBlock extends Block {

    public PFLBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    public PFLBlock() {
        this(Properties.of());
    }
}
