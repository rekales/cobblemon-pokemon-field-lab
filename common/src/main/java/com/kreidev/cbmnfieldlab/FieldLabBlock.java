package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import com.mojang.serialization.DataResult;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.*;

// Some parts are based from DoorBlock, some from PCBlock
public class FieldLabBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public FieldLabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    public FieldLabBlock() {
        this(Properties.of());
    }


    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf buf) {
                    if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER)
                        buf.writeBlockPos(blockPos.below());
                    else
                        buf.writeBlockPos(blockPos);

                    LOGGER.warn(QuestManager.INSTANCE.getPlayerQuests()+"");

                    CompoundTag tag = new CompoundTag();
                    DataResult<Tag> result = PlayerQuestContainer.CODEC.codec()
                            .encodeStart(NbtOps.INSTANCE, QuestManager.getQuestContainer(serverPlayer));
                    result.resultOrPartial(error->LOGGER.error("PlayerQuestContainer data was not saved \n"+error))
                            .ifPresent(nbt -> tag.put("quest_container", nbt));
//
                    LOGGER.warn(QuestManager.INSTANCE.getPlayerQuests()+"");

//                    PlayerQuestContainer container = QuestManager.getQuestContainer(serverPlayer);
//                    PlayerQuestContainer.save(tag, serverLevel.registryAccess(), container);
                    buf.writeNbt(tag);
                }

                @Override
                public @NotNull Component getDisplayName() {
                    //noinspection NoTranslation
                    return Component.translatable("menu.%s.%s", MOD_ID, FIELD_LAB_NAME);
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new FieldLabMenu(i, inventory, blockPos, (ServerPlayer) player);
                }
            });
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {

         VoxelShape SOUTH_AABB_TOP = Shapes.or(
                 Block.box(1, 0, 2, 2, 14, 14),
                 Block.box(2, 2, 6, 14, 13, 14),
                 Block.box(2, 13, 1, 14, 15, 14),
                 Block.box(2, 0, 5, 14, 2, 14),
                 Block.box(14, 0, 2, 15, 14, 14),
                 Block.box(2, 0, 14, 14, 15, 15)
        );

        VoxelShape SOUTH_AABB_BOTTOM = Shapes.or(
                Block.box(2, 0, 2, 14, 1, 14),
                Block.box(2, 8, 3, 14, 16, 14),
                Block.box(2, 1, 1, 14, 8, 14),
                Block.box(1, 1, 2, 2, 16, 14),
                Block.box(14, 1, 2, 15, 16, 14),
                Block.box(2, 0, 14, 14, 16, 15)
        );

        return switch (blockState.getValue(HALF)) {
            case DoubleBlockHalf.UPPER -> SOUTH_AABB_TOP;
            case DoubleBlockHalf.LOWER -> SOUTH_AABB_BOTTOM;
        };
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return blockState2.getBlock() instanceof FieldLabBlock && blockState2.getValue(HALF) != doubleBlockHalf ? blockState2.setValue(HALF, doubleBlockHalf) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos)
                    ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        }
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = levelReader.getBlockState(blockPosBelow);

        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return blockStateBelow.isFaceSturdy(levelReader, blockPosBelow, Direction.UP);
        } else {
            return blockStateBelow.is(this);
        }
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(blockState))) {
            // TODO: access transformer
            // DoublePlantBlock.preventDropFromBottomPart(level, blockPos, blockState, player);
        }

        return super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        level.setBlock(blockPos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        level.blockUpdated(blockPos, Blocks.AIR);
        blockState.updateNeighbourShapes(level, blockPos, 3);
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(HALF);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();

        if (blockPos.getY() < level.getMaxBuildHeight()-1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext)) {
            return this.defaultBlockState()
                    .setValue(FACING, blockPlaceContext.getHorizontalDirection())
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }
}
