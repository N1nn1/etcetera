package com.ninni.etcetera.block;

import com.mojang.serialization.MapCodec;
import com.ninni.etcetera.block.entity.RedstoneWireComparatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedstoneWireComparatorBlock extends DiodeBlock implements EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;
    public static final MapCodec<RedstoneWireComparatorBlock> CODEC = simpleCodec(RedstoneWireComparatorBlock::new);

    public RedstoneWireComparatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false).setValue(MODE, ComparatorMode.COMPARE));
    }

    @Override
    protected int getDelay(@NotNull BlockState state) {
        return 2;
    }

    @Override
    protected int getOutputSignal(BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof RedstoneWireComparatorBlockEntity) {
            return ((RedstoneWireComparatorBlockEntity) blockEntity).getOutputSignal();
        }
        return 0;
    }

    @Override
    protected boolean shouldTurnOn(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        int i = this.getInputSignal(world, pos, state);
        if (i == 0) {
            return false;
        }
        int j = this.getAlternateSignal(world, pos, state);
        if (i > j) {
            return true;
        }
        return i == j && state.getValue(MODE) == ComparatorMode.COMPARE;
    }

    @Override
    protected void checkTickOnNeighbor(Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (world.getBlockTicks().hasScheduledTick(pos, this)) {
            return;
        }

        int calculatedOutput = this.calculateOutputSignal(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        int currentOutput = blockEntity instanceof RedstoneWireComparatorBlockEntity comparatorEntity
                ? comparatorEntity.getOutputSignal()
                : 0;

        if (calculatedOutput != currentOutput || state.getValue(POWERED) != this.shouldTurnOn(world, pos, state)) {
            TickPriority tickPriority = this.shouldPrioritize(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
            world.scheduleTick(pos, this, 2, tickPriority);
        }
    }

    @Override
    protected int getInputSignal(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        int i = super.getInputSignal(world, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.hasAnalogOutputSignal()) {
            i = blockState.getAnalogOutputSignal(world, blockPos);
        } else if (i < 15 && blockState.isRedstoneConductor(world, blockPos)) {
            blockPos = blockPos.relative(direction);
            blockState = world.getBlockState(blockPos);
            ItemFrame itemFrameEntity = this.getAttachedItemFrame(world, direction, blockPos);
            int j = Math.max(itemFrameEntity == null ? Integer.MIN_VALUE : itemFrameEntity.getAnalogOutput(), blockState.hasAnalogOutputSignal() ? blockState.getAnalogOutputSignal(world, blockPos) : Integer.MIN_VALUE);
            if (j != Integer.MIN_VALUE) {
                i = j;
            }
        }
        return i;
    }

    @Nullable
    private ItemFrame getAttachedItemFrame(Level world, Direction facing, BlockPos pos) {
        List<ItemFrame> list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), itemFrame -> itemFrame != null && itemFrame.getDirection() == facing);
        if (list.size() == 1) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        }
        float f = (state = state.cycle(MODE)).getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f;
        world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3f, f);
        world.setBlock(pos, state, Block.UPDATE_CLIENTS);
        this.update(world, pos, state);
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    private void update(Level world, BlockPos pos, BlockState state) {
        int i = this.calculateOutputSignal(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        int j = 0;
        if (blockEntity instanceof RedstoneWireComparatorBlockEntity comparatorBlockEntity) {
            j = comparatorBlockEntity.getOutputSignal();
            comparatorBlockEntity.setOutputSignal(i);
        }
        if (j != i || state.getValue(MODE) == ComparatorMode.COMPARE) {
            boolean bl = this.shouldTurnOn(world, pos, state);
            boolean bl2 = state.getValue(POWERED);
            if (bl2 && !bl) {
                world.setBlock(pos, state.setValue(POWERED, false), Block.UPDATE_CLIENTS);
            } else if (!bl2 && bl) {
                world.setBlock(pos, state.setValue(POWERED, true), Block.UPDATE_CLIENTS);
            }
            this.updateNeighborsInFront(world, pos, state);
        }
    }

    @Override
    public boolean triggerEvent(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, int type, int data) {
        super.triggerEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(type, data);
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.update(world, pos, state);
    }

    private int calculateOutputSignal(Level world, BlockPos pos, BlockState state) {
        int i = this.getInputSignal(world, pos, state);
        if (i == 0) {
            return 0;
        }
        int j = this.getAlternateSignal(world, pos, state);
        if (j > i) {
            return 0;
        }
        if (state.getValue(MODE) == ComparatorMode.SUBTRACT) {
            return i - j;
        }
        return i;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new RedstoneWireComparatorBlockEntity(pos, state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }

    @Override
    protected @NotNull MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }
}