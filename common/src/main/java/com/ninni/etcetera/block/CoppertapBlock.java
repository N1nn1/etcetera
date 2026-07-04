package com.ninni.etcetera.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraParticleTypes;
import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

public class CoppertapBlock extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<CoppertapBlock> CODEC = simpleCodec(CoppertapBlock::new);
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.box(6, 7, 12, 10, 11, 16),
                    Block.box(7, 11, 11, 9, 13, 13),
                    Block.box(6, 5, 8, 10, 11, 12),
                    Block.box(5, 4, 7, 11, 5, 13)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.SOUTH, Stream.of(
                    Block.box(6, 7, 0, 10, 11, 4),
                    Block.box(7, 11, 3, 9, 13, 5),
                    Block.box(6, 5, 4, 10, 11, 8),
                    Block.box(5, 4, 3, 11, 5, 9)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.EAST, Stream.of(
                    Block.box(0, 7, 6, 4, 11, 10),
                    Block.box(3, 11, 7, 5, 13, 9),
                    Block.box(4, 5, 6, 8, 11, 10),
                    Block.box(3, 4, 5, 9, 5, 11)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.box(12, 7, 6, 16, 11, 10),
                    Block.box(11, 11, 7, 13, 13, 9),
                    Block.box(8, 5, 6, 12, 11, 10),
                    Block.box(7, 4, 5, 13, 5, 11)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
    ));

    public CoppertapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(POWERED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        world.setBlock(pos, state.setValue(POWERED, !state.getValue(POWERED)), 3);
        world.playSound(player, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1, 1);
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {

        if (state.getValue(POWERED)) {
            BlockState state2 = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));

            double d = 0.5 - state.getValue(FACING).getStepX() * 0.2F;
            double e = 0.2f;
            double f = 0.5 - state.getValue(FACING).getStepZ() * 0.2F;

            if (random.nextInt(com.ninni.etcetera.config.ModConfig.get().coppertapDripChance) == 0) {
                if (state2.is(EtceteraTags.TAP_RUBBER))
                    world.addParticle(EtceteraParticleTypes.DRIPPING_RUBBER, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.is(EtceteraTags.TAP_HONEY))
                    world.addParticle(ParticleTypes.DRIPPING_HONEY, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.is(EtceteraTags.TAP_CRYING_OBSIDIAN))
                    world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.is(EtceteraTags.TAP_WATER) || state2.getFluidState().is(Fluids.WATER))
                    world.addParticle(ParticleTypes.DRIPPING_WATER, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
                else if (state2.is(EtceteraTags.TAP_LAVA) || state2.getFluidState().is(Fluids.LAVA))
                    world.addParticle(ParticleTypes.DRIPPING_LAVA, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + f, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        if (state.getValue(POWERED) && world.getBlockState(pos.below()).is(Blocks.CAULDRON) && world.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).is(BlockTags.LOGS_THAT_BURN)) {
            if (random.nextInt(com.ninni.etcetera.config.ModConfig.get().coppertapFillCauldronChance) == 0) {
                world.setBlock(pos.below(), EtceteraBlocks.RUBBER_CAULDRON.get().defaultBlockState(), 3);
            }
        }

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (world.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).is(EtceteraTags.TAP_ALWAYS_PLACEABLE))
            return true;
        return world.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).isFaceSturdy(world, pos.relative(state.getValue(FACING).getOpposite()), state.getValue(FACING));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        for (Direction direction : ctx.getNearestLookingDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = blockState.setValue(FACING, direction.getOpposite())).canSurvive(ctx.getLevel(), ctx.getClickedPos()))
                continue;
            LevelAccessor worldAccess = ctx.getLevel();
            BlockPos blockPos = ctx.getClickedPos();
            boolean bl = worldAccess.getFluidState(blockPos).getType() == Fluids.WATER;
            return blockState.setValue(WATERLOGGED, bl);
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, WATERLOGGED, POWERED));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }
}