package com.ninni.etcetera.block;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import com.ninni.etcetera.registry.EtceteraStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("deprecation")
public class DiceBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DiceBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(POWERED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        trigger(state, world, pos);
        player.awardStat(EtceteraStats.ROTATE_DICE.get());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.hasNeighborSignal(pos);
        boolean bl2 = state.getValue(POWERED);
        if (powered && !bl2) {
            world.scheduleTick(pos, this, 4);
            world.setBlock(pos, state.setValue(POWERED, true), 4);
        } else if (!powered && bl2) {
            world.setBlock(pos, state.setValue(POWERED, false), 4);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        trigger(state, world, pos);
    }

    public void trigger(BlockState state, Level world, BlockPos pos) {
        Direction[] directions = Direction.values();
        if (!world.isClientSide) world.setBlockAndUpdate(pos, state.setValue(FACING, directions[world.getRandom().nextInt(directions.length)]));
        world.playSound(null, pos, EtceteraSoundEvents.BLOCK_DICE_ROLL.get(), SoundSource.BLOCKS, 1, 1);
    }

    public static int calculateComparatorOutput(BlockState state) {
        int p;
        switch (state.getValue(FACING)) {
            case UP -> p = 1;
            case DOWN -> p = 6;
            case NORTH -> p = 3;
            case SOUTH -> p = 4;
            case EAST -> p = 2;
            default -> p = 5;
        }
        return p;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState p_60457_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return calculateComparatorOutput(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, Direction.NORTH);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

}
