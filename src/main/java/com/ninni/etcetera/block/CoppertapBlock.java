package com.ninni.etcetera.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraParticleTypes;
import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

public class CoppertapBlock extends Block {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(6, 7, 12, 10, 11, 16),
                    Block.createCuboidShape(7, 11, 11, 9, 13, 13),
                    Block.createCuboidShape(6, 5, 8, 10, 11, 12),
                    Block.createCuboidShape(5, 4, 7, 11, 5, 13)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(6, 7, 0, 10, 11, 4),
                    Block.createCuboidShape(7, 11, 3, 9, 13, 5),
                    Block.createCuboidShape(6, 5, 4, 10, 11, 8),
                    Block.createCuboidShape(5, 4, 3, 11, 5, 9)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(0, 7, 6, 4, 11, 10),
                    Block.createCuboidShape(3, 11, 7, 5, 13, 9),
                    Block.createCuboidShape(4, 5, 6, 8, 11, 10),
                    Block.createCuboidShape(3, 4, 5, 9, 5, 11)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(12, 7, 6, 16, 11, 10),
                    Block.createCuboidShape(11, 11, 7, 13, 13, 9),
                    Block.createCuboidShape(8, 5, 6, 12, 11, 10),
                    Block.createCuboidShape(7, 4, 5, 13, 5, 11)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get()
    ));

    public CoppertapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty()) {
            world.setBlockState(pos, state.with(POWERED, !state.get(POWERED)));
            world.playSound(player, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 1, 1);
            return ActionResult.success(true);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if (state.get(POWERED)) {
            BlockState state2 = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));

            double d = 0.5 - state.get(FACING).getOffsetX() * 0.2F;
            double e = 0.2f;
            double f = 0.5 - state.get(FACING).getOffsetZ() * 0.2F;

            if (random.nextInt(5) == 0) {
                if (state2.isIn(EtceteraTags.TAP_RUBBER)) world.addParticle(EtceteraParticleTypes.DRIPPING_RUBBER, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.isIn(EtceteraTags.TAP_HONEY)) world.addParticle(ParticleTypes.DRIPPING_HONEY, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.isIn(EtceteraTags.TAP_CRYING_OBSIDIAN)) world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.isIn(EtceteraTags.TAP_WATER) || state2.getFluidState().isOf(Fluids.WATER)) world.addParticle(ParticleTypes.DRIPPING_WATER, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.isIn(EtceteraTags.TAP_LAVA) || state2.getFluidState().isOf(Fluids.LAVA)) world.addParticle(ParticleTypes.DRIPPING_LAVA, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        if (state.get(POWERED) && world.getBlockState(pos.down()).isOf(Blocks.CAULDRON) && world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isIn(BlockTags.LOGS_THAT_BURN)) {
            if (random.nextInt(7) == 0) {
                world.setBlockState(pos.down(), EtceteraBlocks.RUBBER_CAULDRON.getDefaultState());
            }
        }

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }

    @Override public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isIn(EtceteraTags.TAP_ALWAYS_PLACEABLE)) return true;
        return world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isSideSolidFullSquare(world, pos.offset(state.get(FACING).getOpposite()), state.get(FACING));
    }

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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) { return Blocks.AIR.getDefaultState(); }
        return state;
    }

    @Override public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }

    @Override public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { super.appendProperties(builder.add(FACING, WATERLOGGED, POWERED)); }
}
