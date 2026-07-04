package com.ninni.etcetera.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;

public class RedstoneWiresBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_WEST = BlockStateProperties.WEST_REDSTONE;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final Map<Direction, EnumProperty<RedstoneSide>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape DOT_SHAPE =
            Block.box(3.0, 0.0, 3.0, 13.0, 2.0, 13.0);
    private static final Map<Direction, VoxelShape> DIRECTION_TO_SIDE_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(3.0, 0.0, 0.0, 13.0, 2.0, 13.0),
            Direction.SOUTH, Block.box(3.0, 0.0, 3.0, 13.0, 2.0, 16.0),
            Direction.EAST, Block.box(3.0, 0.0, 3.0, 16.0, 2.0, 13.0),
            Direction.WEST, Block.box(0.0, 0.0, 3.0, 13.0, 2.0, 13.0)));
    private static final Map<Direction, VoxelShape> DIRECTION_TO_UP_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.or(DIRECTION_TO_SIDE_SHAPE.get(Direction.NORTH),
                    Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 2.0)),
            Direction.SOUTH, Shapes.or(DIRECTION_TO_SIDE_SHAPE.get(Direction.SOUTH),
                    Block.box(3.0, 0.0, 14.0, 13.0, 16.0, 16.0)),
            Direction.EAST, Shapes.or(DIRECTION_TO_SIDE_SHAPE.get(Direction.EAST),
                    Block.box(14.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
            Direction.WEST, Shapes.or(DIRECTION_TO_SIDE_SHAPE.get(Direction.WEST),
                    Block.box(0.0, 0.0, 3.0, 2.0, 16.0, 13.0))));
    private static final Map<BlockState, VoxelShape> SHAPES = Maps.newHashMap();
    public static final MapCodec<RedstoneWiresBlock> CODEC = simpleCodec(RedstoneWiresBlock::new);
    private static final Vec3[] COLORS = Util.make(new Vec3[16], colors -> {
        for (int i = 0; i <= 15; ++i) {
            float f = (float) i / 15.0f;
            float g = f * 0.6f + (f > 0.0f ? 0.4f : 0.3f);
            float h = Mth.clamp(f * f * 0.7f - 0.5f, 0.0f, 1.0f);
            float j = Mth.clamp(f * f * 0.6f - 0.7f, 0.0f, 1.0f);
            colors[i] = new Vec3(g, h, j);
        }
    });
    private final BlockState dotState;
    private boolean wiresGivePower = true;

    public RedstoneWiresBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(WIRE_CONNECTION_NORTH, RedstoneSide.NONE).setValue(WIRE_CONNECTION_EAST, RedstoneSide.NONE).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.NONE).setValue(WIRE_CONNECTION_WEST, RedstoneSide.NONE).setValue(POWER, 0));
        this.dotState = (((this.defaultBlockState().setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE)).setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);
        for (BlockState blockState : this.getStateDefinition().getPossibleStates()) {
            if (blockState.getValue(POWER) != 0) continue;
            SHAPES.put(blockState, this.getShapeForState(blockState));
        }
    }

    private static boolean isFullyConnected(BlockState state) {
        return state.getValue(WIRE_CONNECTION_NORTH).isConnected() && state.getValue(WIRE_CONNECTION_SOUTH).isConnected() && state.getValue(WIRE_CONNECTION_EAST).isConnected() && state.getValue(WIRE_CONNECTION_WEST).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !state.getValue(WIRE_CONNECTION_NORTH).isConnected() && !state.getValue(WIRE_CONNECTION_SOUTH).isConnected() && !state.getValue(WIRE_CONNECTION_EAST).isConnected() && !state.getValue(WIRE_CONNECTION_WEST).isConnected();
    }

    protected static boolean connectsTo(BlockState state) {
        return RedstoneWiresBlock.connectsTo(state, null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.is(EtceteraBlocks.REDSTONE_WIRES.get())) return true;
        if (state.is(Blocks.REDSTONE_WIRE)) return false;

        if (state.is(Blocks.REPEATER) || state.is(EtceteraBlocks.REDSTONE_WIRE_REPEATER.get())) {
            Direction direction = state.getValue(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        }
        if (state.is(Blocks.OBSERVER)) {
            return dir == state.getValue(ObserverBlock.FACING);
        }
        return state.isSignalSource() && dir != null;
    }

    public static int getWireColor(int powerLevel) {
        Vec3 vec3d = COLORS[powerLevel];
        return Mth.color((float) vec3d.x(), (float) vec3d.y(), (float) vec3d.z());
    }

    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = DOT_SHAPE;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide wireConnection = state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == RedstoneSide.SIDE) {
                voxelShape = Shapes.or(voxelShape, DIRECTION_TO_SIDE_SHAPE.get(direction));
                continue;
            }
            if (wireConnection != RedstoneSide.UP) continue;
            voxelShape = Shapes.or(voxelShape, DIRECTION_TO_UP_SHAPE.get(direction));
        }
        return voxelShape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.setValue(POWER, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.getStateForPlacement(ctx.getLevel(), this.dotState, ctx.getClickedPos()).setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    private BlockState getStateForPlacement(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = RedstoneWiresBlock.isNotConnected(state);
        state = this.getDefaultWireState(world, this.defaultBlockState().setValue(POWER, state.getValue(POWER)), pos);
        if (bl && RedstoneWiresBlock.isNotConnected(state)) {
            return state;
        }
        boolean bl2 = state.getValue(WIRE_CONNECTION_NORTH).isConnected();
        boolean bl3 = state.getValue(WIRE_CONNECTION_SOUTH).isConnected();
        boolean bl4 = state.getValue(WIRE_CONNECTION_EAST).isConnected();
        boolean bl5 = state.getValue(WIRE_CONNECTION_WEST).isConnected();
        boolean bl6 = !bl2 && !bl3;
        boolean bl7 = !bl4 && !bl5;
        if (!bl5 && bl6) {
            state = state.setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);
        }
        if (!bl4 && bl6) {
            state = state.setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE);
        }
        if (!bl2 && bl7) {
            state = state.setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE);
        }
        if (!bl3 && bl7) {
            state = state.setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE);
        }
        return state;
    }

    private BlockState getDefaultWireState(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.above()).isRedstoneConductor(world, pos);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected()) continue;
            RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction, bl);
            state = state.setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
        }
        return state;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        if (direction == Direction.DOWN) {
            return state;
        }
        if (direction == Direction.UP) {
            return this.getStateForPlacement(world, state, pos).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
        }
        RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction);
        if (wireConnection.isConnected() == (state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() && !RedstoneWiresBlock.isFullyConnected(state)) {
            return state.setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
        }
        return this.getStateForPlacement(world, (this.dotState.setValue(POWER, state.getValue(POWER))).setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    public void updateIndirectNeighbourShapes(@NotNull BlockState state, @NotNull LevelAccessor world, @NotNull BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide wireConnection = state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == RedstoneSide.NONE || (world.getBlockState(mutable.setWithOffset(pos, direction)).is(this)))
                continue;
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.is(this)) {
                BlockPos blockPos = mutable.relative(direction.getOpposite());
                world.neighborShapeChanged(direction.getOpposite(), world.getBlockState(blockPos), mutable, blockPos, flags, maxUpdateDepth);
            }
            mutable.setWithOffset(pos, direction).move(Direction.UP);
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.is(this)) continue;
            BlockPos blockPos2 = mutable.relative(direction.getOpposite());
            world.neighborShapeChanged(direction.getOpposite(), world.getBlockState(blockPos2), mutable, blockPos2, flags, maxUpdateDepth);
        }
    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction) {
        return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.above()).isRedstoneConductor(world, pos));
    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction, boolean bl) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (bl) {
            boolean bl2 = blockState.getBlock() instanceof TrapDoorBlock || this.canRunOnTop(world, blockPos, blockState);
            if (bl2 && RedstoneWiresBlock.connectsTo(world.getBlockState(blockPos.above()))) {
                if (blockState.isFaceSturdy(world, blockPos, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }
                return RedstoneSide.SIDE;
            }
        }
        if (RedstoneWiresBlock.connectsTo(blockState, direction) || !blockState.isRedstoneConductor(world, blockPos) && RedstoneWiresBlock.connectsTo(world.getBlockState(blockPos.below()))) {
            return RedstoneSide.SIDE;
        }
        return RedstoneSide.NONE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, blockState);
    }

    private boolean canRunOnTop(BlockGetter world, BlockPos pos, BlockState floor) {
        return floor.isFaceSturdy(world, pos, Direction.UP) || floor.is(Blocks.HOPPER);
    }

    private void update(Level world, BlockPos pos, BlockState state) {
        int i = this.getBestNeighborSignal(world, pos);
        if (state.getValue(POWER) != i) {
            if (world.getBlockState(pos) == state) {
                world.setBlock(pos, state.setValue(POWER, i).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), Block.UPDATE_CLIENTS);
            }
            HashSet<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            for (Direction direction : Direction.values()) {
                set.add(pos.relative(direction));
            }
            for (BlockPos blockPos : set) {
                world.updateNeighborsAt(blockPos, this);
            }
        }
    }

    private int getBestNeighborSignal(Level world, BlockPos pos) {
        this.wiresGivePower = false;
        int i = world.getBestNeighborSignal(pos);
        this.wiresGivePower = true;
        int j = 0;
        if (i < 15) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos);
                j = Math.max(j, this.increasePower(blockState));
                BlockPos blockPos2 = pos.above();
                if (blockState.isRedstoneConductor(world, blockPos) && !world.getBlockState(blockPos2).isRedstoneConductor(world, blockPos2)) {
                    j = Math.max(j, this.increasePower(world.getBlockState(blockPos.above())));
                    continue;
                }
                if (blockState.isRedstoneConductor(world, blockPos)) continue;
                j = Math.max(j, this.increasePower(world.getBlockState(blockPos.below())));
            }
        }
        return Math.max(i, j - 1);
    }

    private int increasePower(BlockState state) {
        return (state.is(this)) ? state.getValue(POWER) : 0;
    }

    private void updateNeighbors(Level world, BlockPos pos) {
        if (!world.getBlockState(pos).is(this)) {
            return;
        }
        world.updateNeighborsAt(pos, this);
        for (Direction direction : Direction.values()) {
            world.updateNeighborsAt(pos.relative(direction), this);
        }
    }

    @Override
    public void onPlace(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.is(state.getBlock()) || world.isClientSide) {
            return;
        }
        this.update(world, pos, state);
        for (Direction direction : Direction.Plane.VERTICAL) {
            world.updateNeighborsAt(pos.relative(direction), this);
        }
        this.updateOffsetNeighbors(world, pos);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean moved) {
        if (moved || state.is(newState.getBlock())) {
            return;
        }
        super.onRemove(state, world, pos, newState, moved);
        if (world.isClientSide) {
            return;
        }
        for (Direction direction : Direction.values()) {
            world.updateNeighborsAt(pos.relative(direction), this);
        }
        this.update(world, pos, state);
        this.updateOffsetNeighbors(world, pos);
    }

    private void updateOffsetNeighbors(Level world, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            this.updateNeighbors(world, pos.relative(direction));
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos = pos.relative(direction);
            if (world.getBlockState(blockPos).isRedstoneConductor(world, blockPos)) {
                this.updateNeighbors(world, blockPos.above());
                continue;
            }
            this.updateNeighbors(world, blockPos.below());
        }
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Block sourceBlock, @NotNull BlockPos sourcePos, boolean notify) {
        if (world.isClientSide) {
            return;
        }
        if (state.canSurvive(world, pos)) {
            this.update(world, pos, state);
        } else {
            Block.dropResources(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public int getDirectSignal(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
        if (!this.wiresGivePower) return 0;
        return state.getSignal(world, pos, direction);
    }

    @Override
    public int getSignal(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction direction) {
        if (!this.wiresGivePower || direction == Direction.DOWN) return 0;
        int i = state.getValue(POWER);
        if (i == 0) {
            return 0;
        }
        if (direction == Direction.UP || (this.getStateForPlacement(world, state, pos).getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction.getOpposite()))).isConnected()) {
            return i;
        }
        return 0;
    }

    @Override
    protected boolean isSignalSource(@NotNull BlockState state) {
        return this.wiresGivePower;
    }

    private void addPoweredParticles(Level world, RandomSource random, BlockPos pos, Vec3 color, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;
        if (random.nextFloat() >= 0.2f * h) {
            return;
        }
        float i = 0.4375f;
        float j = f + h * random.nextFloat();
        double d = 0.5 + (double) (i * (float) direction.getStepX()) + (double) (j * (float) direction2.getStepX());
        double e = 0.5 + (double) (i * (float) direction.getStepY()) + (double) (j * (float) direction2.getStepY());
        double k = 0.5 + (double) (i * (float) direction.getStepZ()) + (double) (j * (float) direction2.getStepZ());
        world.addParticle(new DustParticleOptions(color.toVector3f(), 1.0f), (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + k, 0.0, 0.0, 0.0);
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        int i = state.getValue(POWER);
        if (i == 0) {
            return;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide wireConnection = state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            switch (wireConnection) {
                case UP: {
                    this.addPoweredParticles(world, random, pos, COLORS[i], direction, Direction.UP, -0.5f, 0.5f);
                }
                case SIDE: {
                    this.addPoweredParticles(world, random, pos, COLORS[i], Direction.DOWN, direction, 0.0f, 0.5f);
                    continue;
                }
            }
            this.addPoweredParticles(world, random, pos, COLORS[i], Direction.DOWN, direction, 0.0f, 0.3f);
        }
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    (((state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_NORTH))).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_EAST));
            case COUNTERCLOCKWISE_90 ->
                    (((state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_EAST))).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_NORTH));
            case CLOCKWISE_90 ->
                    (((state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_NORTH))).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_EAST))).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_SOUTH));
            default -> state;
        };
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT ->
                    (state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_SOUTH))).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_NORTH));
            case FRONT_BACK ->
                    (state.setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_WEST))).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST, POWER, WATERLOGGED);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        }
        if (RedstoneWiresBlock.isFullyConnected(state) || RedstoneWiresBlock.isNotConnected(state)) {
            BlockState blockState = RedstoneWiresBlock.isFullyConnected(state) ? this.defaultBlockState() : this.dotState;
            blockState = blockState.setValue(POWER, state.getValue(POWER));
            if ((blockState = this.getStateForPlacement(world, blockState, pos)) != state) {
                world.setBlock(pos, blockState.setValue(WATERLOGGED, state.getValue(WATERLOGGED)), Block.UPDATE_ALL);
                this.updateForNewState(world, pos, state, blockState);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void updateForNewState(Level world, BlockPos pos, BlockState oldState, BlockState newState) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos = pos.relative(direction);
            if (oldState.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() == (newState.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))).isConnected() || !world.getBlockState(blockPos).isRedstoneConductor(world, blockPos))
                continue;
            world.updateNeighborsAtExceptFromFacing(blockPos, newState.getBlock(), direction.getOpposite());
        }
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }
}