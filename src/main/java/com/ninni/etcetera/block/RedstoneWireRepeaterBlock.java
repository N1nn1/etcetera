package com.ninni.etcetera.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class RedstoneWireRepeaterBlock extends RepeaterBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public RedstoneWireRepeaterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(DELAY, 1)).with(LOCKED, false)).with(POWERED, false).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        return blockState.with(LOCKED, this.isLocked(ctx.getWorld(), ctx.getBlockPos(), blockState)).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, POWERED, LOCKED, DELAY);
    }
}
