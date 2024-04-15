package com.ninni.etcetera.block;

import com.ninni.etcetera.block.entity.RedstoneWireComparatorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.TickPriority;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedstoneWireComparatorBlock extends AbstractRedstoneGateBlock implements BlockEntityProvider {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<ComparatorMode> MODE = Properties.COMPARATOR_MODE;

    public RedstoneWireComparatorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(WATERLOGGED, false).with(MODE, ComparatorMode.COMPARE));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof RedstoneWireComparatorBlockEntity) {
            return ((RedstoneWireComparatorBlockEntity)blockEntity).getOutputSignal();
        }
        return 0;
    }

    @Override
    protected boolean hasPower(World world, BlockPos pos, BlockState state) {
        int i = this.getPower(world, pos, state);
        if (i == 0) {
            return false;
        }
        int j = this.getMaxInputLevelSides(world, pos, state);
        if (i > j) {
            return true;
        }
        return i == j && state.get(MODE) == ComparatorMode.COMPARE;
    }

    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        int j;
        if (world.getBlockTickScheduler().isTicking(pos, this)) {
            return;
        }
        int i = this.calculateOutputSignal(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        int n = j = blockEntity instanceof RedstoneWireComparatorBlockEntity ? ((RedstoneWireComparatorBlockEntity)blockEntity).getOutputSignal() : 0;
        if (i != j || state.get(POWERED) != this.hasPower(world, pos, state)) {
            TickPriority tickPriority = this.isTargetNotAligned(world, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
            world.scheduleBlockTick(pos, this, 2, tickPriority);
        }
    }

    @Override
    protected int getPower(World world, BlockPos pos, BlockState state) {
        int i = super.getPower(world, pos, state);
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.hasComparatorOutput()) {
            i = blockState.getComparatorOutput(world, blockPos);
        } else if (i < 15 && blockState.isSolidBlock(world, blockPos)) {
            blockPos = blockPos.offset(direction);
            blockState = world.getBlockState(blockPos);
            ItemFrameEntity itemFrameEntity = this.getAttachedItemFrame(world, direction, blockPos);
            int j = Math.max(itemFrameEntity == null ? Integer.MIN_VALUE : itemFrameEntity.getComparatorPower(), blockState.hasComparatorOutput() ? blockState.getComparatorOutput(world, blockPos) : Integer.MIN_VALUE);
            if (j != Integer.MIN_VALUE) {
                i = j;
            }
        }
        return i;
    }

    @Nullable
    private ItemFrameEntity getAttachedItemFrame(World world, Direction facing, BlockPos pos) {
        List<ItemFrameEntity> list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), itemFrame -> itemFrame != null && itemFrame.getHorizontalFacing() == facing);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        }
        float f = (state = state.cycle(MODE)).get(MODE) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f;
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        this.update(world, pos, state);
        return ActionResult.success(world.isClient);
    }

    private void update(World world, BlockPos pos, BlockState state) {
        int i = this.calculateOutputSignal(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        int j = 0;
        if (blockEntity instanceof RedstoneWireComparatorBlockEntity comparatorBlockEntity) {
            j = comparatorBlockEntity.getOutputSignal();
            comparatorBlockEntity.setOutputSignal(i);
        }
        if (j != i || state.get(MODE) == ComparatorMode.COMPARE) {
            boolean bl = this.hasPower(world, pos, state);
            boolean bl2 = state.get(POWERED);
            if (bl2 && !bl) {
                world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
            } else if (!bl2 && bl) {
                world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_LISTENERS);
            }
            this.updateTarget(world, pos, state);
        }
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.update(world, pos, state);
    }

    private int calculateOutputSignal(World world, BlockPos pos, BlockState state) {
        int i = this.getPower(world, pos, state);
        if (i == 0) {
            return 0;
        }
        int j = this.getMaxInputLevelSides(world, pos, state);
        if (j > i) {
            return 0;
        }
        if (state.get(MODE) == ComparatorMode.SUBTRACT) {
            return i - j;
        }
        return i;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneWireComparatorBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, MODE, POWERED);
    }
}
