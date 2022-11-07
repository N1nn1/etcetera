package com.ninni.etcetera.block;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.block.enums.EtceteraColumnShape;
import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class ColumnBlock extends Block {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<EtceteraColumnShape> SHAPE = EtceteraProperties.SHAPE;
    public static final VoxelShape BASE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    protected static final VoxelShape TIP_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 8, 0, 16, 16, 16), Block.createCuboidShape(2, 0, 2, 14, 8, 14), BooleanBiFunction.OR);

    public ColumnBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(SHAPE, EtceteraColumnShape.TIP));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state.with(SHAPE, getColumnShape(world, pos));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isOf(EtceteraItems.HAMMER)) {
            world.breakBlock(pos, false, null);
            world.setBlockState(pos, state.with(SHAPE, EtceteraColumnShape.TIP));
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(SHAPE) == EtceteraColumnShape.TIP) return TIP_SHAPE;

        return BASE_SHAPE;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World worldAccess = ctx.getWorld();
        EtceteraColumnShape shape = getColumnShape(worldAccess, blockPos);
        if (shape == null) return null;
        return this.getDefaultState().with(SHAPE, shape).with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
    }

    private EtceteraColumnShape getColumnShape(WorldAccess world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        BlockState upState = world.getBlockState(pos.up());
        return (downState.getBlock() instanceof ColumnBlock && upState.getBlock() instanceof ColumnBlock) || upState.getBlock() instanceof ColumnBlock ? EtceteraColumnShape.BASE : EtceteraColumnShape.TIP;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SHAPE);
    }
}
