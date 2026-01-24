package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.gui.FieldLabMenu;
import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import com.kreidev.cbmnfieldlab.util.VoxelShaper;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.*;

// Some parts are based from DoorBlock, some from PCBlock
@MethodsReturnNonnullByDefault
public class FieldLabBlock extends BaseEntityBlock {

    public static final MapCodec<FieldLabBlock> CODEC = simpleCodec(FieldLabBlock::new);

    // Raw cubes and non-optimal but should be fine since there should only be a few instances of this
    private static final VoxelShaper SHAPER = VoxelShaper.forHorizontal(Shapes.or(
            Block.box(7, 13, 12, 11, 14, 13),
            Block.box(3, 3, 12, 15, 13, 15),
            Block.box(7, 2, 1, 11, 3, 2),
            Block.box(3, 0, 2, 15, 3, 12),
            Block.box(4, 3, 3, 14, 4, 12),
            Block.box(5, 4, 11, 13, 11, 12),
            Block.box(5, 11, 10, 13, 12, 12),
            Block.box(13, 4, 10, 14, 12, 12),
            Block.box(4, 4, 10, 5, 12, 12),
            Block.box(1, 4, 11, 3, 10, 14),
            Block.box(0, 1, 5, 3, 2, 9),
            Block.box(1, 2, 6, 3, 4, 8),
            Block.box(2, 10, 13, 3, 17, 14)
    ), Direction.SOUTH);

    public static final VoxelShape NORTH_AABB = SHAPER.get(Direction.NORTH);
    public static final VoxelShape SOUTH_AABB = SHAPER.get(Direction.SOUTH);
    public static final VoxelShape EAST_AABB = SHAPER.get(Direction.EAST);
    public static final VoxelShape WEST_AABB = SHAPER.get(Direction.WEST);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public FieldLabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
            MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf buf) {
                    buf.writeBlockPos(blockPos);

                    CompoundTag tag = new CompoundTag();
                    DataResult<Tag> result = PlayerQuestContainer.CODEC.codec()
                            .encodeStart(NbtOps.INSTANCE, QuestManager.getQuestContainer(serverPlayer));
                    result.resultOrPartial(error->LOGGER.error("PlayerQuestContainer data was not saved \n"+error))
                            .ifPresent(nbt -> tag.put("quest_container", nbt));

                    buf.writeNbt(tag);
                }

                @Override
                public Component getDisplayName() {
                    //noinspection NoTranslation
                    return Component.translatable("menu.%s.%s", MOD_ID, FIELD_LAB_NAME);
                }

                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new FieldLabMenu(i, inventory, blockPos, (ServerPlayer) player);
                }
            });
            level.setBlockAndUpdate(blockPos, blockState.setValue(FieldLabBlock.OPEN, true));
            if (level.getBlockEntity(blockPos) instanceof FieldLabBlockEntity be) be.setUser(serverPlayer.getUUID());
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case Direction.NORTH -> NORTH_AABB;
            case Direction.SOUTH -> SOUTH_AABB;
            case Direction.EAST -> EAST_AABB;
            default -> WEST_AABB;
        };
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = levelReader.getBlockState(blockPosBelow);

        return blockStateBelow.isFaceSturdy(levelReader, blockPosBelow, Direction.UP);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
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
        builder.add(OPEN);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();

        if (blockPos.getY() < level.getMaxBuildHeight()-1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext)) {
            return this.defaultBlockState()
                    .setValue(FACING, blockPlaceContext.getHorizontalDirection())
                    .setValue(OPEN, false);
        } else {
            return null;
        }
    }

    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FieldLabBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, FIELD_LAB_BLOCK_ENTITY.get() , FieldLabBlockEntity::serverTick);
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
