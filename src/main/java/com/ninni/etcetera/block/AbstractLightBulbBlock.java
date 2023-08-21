package com.ninni.etcetera.block;

import com.ninni.etcetera.block.enums.LightBulbBrightness;
import com.ninni.etcetera.registry.EtceteraProperties;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AbstractLightBulbBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    //Actual brightness. Will update on click or when power of block below (and intern ours) will change
    public static final EnumProperty<LightBulbBrightness> BRIGHTNESS = EtceteraProperties.BRIGHTNESS;
    //what brightness we would have if it only was for power level. could also rename it to power_0_4. Needed so we dont update brightness when power doesnt change
    public static final EnumProperty<LightBulbBrightness> WANTED_BRIGHTNESS = EtceteraProperties.WANTED_BRIGHTNESS;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(3, 3, 3, 13, 16, 13),
            Block.box(3, 0, 3, 13, 13, 13),
            Block.box(3, 3, 3, 13, 13, 16),
            Block.box(3, 3, 0, 13, 13, 13),
            Block.box(3, 3, 3, 16, 13, 13),
            Block.box(0, 3, 3, 13, 13, 13)
    };

    public AbstractLightBulbBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WANTED_BRIGHTNESS, LightBulbBrightness.OFF)
                .setValue(FACING, Direction.DOWN)
                .setValue(WATERLOGGED, false)
                .setValue(BRIGHTNESS, LightBulbBrightness.OFF));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        for (Direction direction : ctx.getNearestLookingDirections()) {
            BlockState blockState = this.defaultBlockState().setValue(FACING, direction.getOpposite());
            if (blockState.canSurvive(level, pos)) {
                LightBulbBrightness brightness = getWantedBrightness(blockState, level, pos);
                return blockState
                        .setValue(WANTED_BRIGHTNESS, brightness)
                        .setValue(BRIGHTNESS, brightness)
                        .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            }
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        this.turnBrightness(state, world, pos, state.getValue(BRIGHTNESS));
        return InteractionResult.SUCCESS;
    }

    public void turnBrightness(BlockState state, Level world, BlockPos pos, LightBulbBrightness brightness) {
        SoundEvent soundEvent = brightness == LightBulbBrightness.BRIGHT ? EtceteraSoundEvents.BLOCK_LIGHT_BULB_OFF.get() : EtceteraSoundEvents.BLOCK_LIGHT_BULB_ON.get();
        world.setBlockAndUpdate(pos, state.cycle(BRIGHTNESS));
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1, 1);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get3DDataValue()];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, WANTED_BRIGHTNESS, BRIGHTNESS);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = attachedDirection(state);
        return Block.canSupportCenter(world, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction attachedDirection(BlockState state) {
        return state.getValue(FACING).getOpposite();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_60543_, LevelAccessor world, BlockPos pos, BlockPos p_60546_) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (attachedDirection(state).getOpposite() == direction && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, p_60543_, world, pos, p_60546_);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
        super.neighborChanged(state, level, pos, block, fromPos, moving);
        LightBulbBrightness newBrightness = getWantedBrightness(state, level, pos);
        if (newBrightness != state.getValue(WANTED_BRIGHTNESS)) {
            SoundEvent soundEvent = newBrightness == LightBulbBrightness.OFF ? EtceteraSoundEvents.BLOCK_LIGHT_BULB_OFF.get() : EtceteraSoundEvents.BLOCK_LIGHT_BULB_ON.get();
            level.setBlockAndUpdate(pos, state
                    .setValue(WANTED_BRIGHTNESS, newBrightness)
                    .setValue(BRIGHTNESS, newBrightness));
            level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1, 1);
        }
    }

    private static LightBulbBrightness getWantedBrightness(BlockState state, Level level, BlockPos pos) {
        Direction dir = attachedDirection(state);
        BlockPos behind = pos.relative(dir);
        int signal = level.getSignal(behind, dir);
        int bi;
        if (signal != 0) {
            bi = (int) Math.floor(signal * 3f / 15f);
        } else bi = 0;
        return LightBulbBrightness.values()[bi];
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }


}