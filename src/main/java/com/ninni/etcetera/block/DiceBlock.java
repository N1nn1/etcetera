package com.ninni.etcetera.block;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import com.ninni.etcetera.stat.EtceteraStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


@SuppressWarnings("deprecation")
public class DiceBlock extends FacingBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final DirectionProperty FACING = Properties.FACING;

    public DiceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        trigger(state, world, pos);
        player.incrementStat(EtceteraStats.ROTATE_DICE);
        return ActionResult.SUCCESS;
    }


    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        boolean bl2 = state.get(POWERED);
        if (powered && !bl2) {
            world.createAndScheduleBlockTick(pos, this, 4);
            world.setBlockState(pos, state.with(POWERED, true), Block.NO_REDRAW);
        } else if (!powered && bl2) {
            world.setBlockState(pos, state.with(POWERED, false), Block.NO_REDRAW);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        trigger(state, world, pos);
    }

    public void trigger(BlockState state, World world, BlockPos pos) {
        Direction[] directions = Direction.values();
        if (!world.isClient) world.setBlockState(pos, state.with(FACING, directions[world.getRandom().nextInt(directions.length)]));
        world.playSound(null, pos, EtceteraSoundEvents.BLOCK_DICE_ROLL, SoundCategory.BLOCKS, 1, 1);
    }

    public static int calculateComparatorOutput(BlockState state) {
        int p;
        switch (state.get(FACING)) {
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
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return calculateComparatorOutput(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, Direction.NORTH);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}
