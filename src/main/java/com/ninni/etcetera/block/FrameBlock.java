package com.ninni.etcetera.block;

import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class FrameBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public FrameBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!context.isAbove(VoxelShapes.fullCube(), pos, true)) return VoxelShapes.empty();
        return VoxelShapes.fullCube();
    }

    @Override public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) { return VoxelShapes.fullCube(); }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return VoxelShapes.fullCube(); }

    @Override public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (context.getPlayer().isHolding(EtceteraItems.FRAME) || context.getPlayer().shouldCancelInteraction()) return false;
        if (!context.getPlayer().isCreative()) {
            if (context.getPlayer().getInventory().getEmptySlot() >= 0) {
                context.getWorld().breakBlock(context.getBlockPos(), false, context.getPlayer());
                context.getPlayer().giveItemStack(EtceteraItems.FRAME.getDefaultStack());
            }
            else context.getWorld().breakBlock(context.getBlockPos(), true, context.getPlayer());
        }

        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override public BlockState getPlacementState(ItemPlacementContext ctx) { return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER); }
    @Override public FluidState getFluidState(BlockState state) { return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state); }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(WATERLOGGED); }
}
