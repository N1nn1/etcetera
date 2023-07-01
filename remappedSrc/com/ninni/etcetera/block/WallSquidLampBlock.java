package com.ninni.etcetera.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallSquidLampBlock extends SquidLampBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
        Direction.NORTH, Block.createCuboidShape(5, 5, 7, 11, 11, 16),
        Direction.SOUTH, Block.createCuboidShape(5, 5, 0, 11, 11, 9),
        Direction.WEST, Block.createCuboidShape(7, 5, 5, 16, 11, 11),
        Direction.EAST, Block.createCuboidShape(0, 5, 5, 9, 11, 11)
    ));

    protected WallSquidLampBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override public String getTranslationKey() { return this.asItem().getTranslationKey(); }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return WallSquidLampBlock.getBoundingShape(state); }

    public static VoxelShape getBoundingShape(BlockState state) { return BOUNDING_SHAPES.get(state.get(FACING)); }

    @Override public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) { return world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isSideSolidFullSquare(world, pos.offset(state.get(FACING).getOpposite()), state.get(FACING)); }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        for (Direction direction : ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = blockState.with(FACING, direction.getOpposite())).canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            WorldAccess worldAccess = ctx.getWorld();
            BlockPos blockPos = ctx.getBlockPos();
            boolean bl = worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER;
            return blockState.with(WATERLOGGED, bl);
        }
        return null;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() + 0.7;
        double z = (double)pos.getZ() + random.nextDouble();
        if (state.get(WATERLOGGED)) world.addParticle(ParticleTypes.GLOW, x, y, z, 0.0, 0.0, 0.0);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) { return Blocks.AIR.getDefaultState(); }
        return state;
    }

    @Override public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }

    @Override public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { super.appendProperties(builder.add(FACING)); }
}


