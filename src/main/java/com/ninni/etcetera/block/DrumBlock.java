package com.ninni.etcetera.block;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

@SuppressWarnings("deprecation")
public class DrumBlock extends Block implements Waterloggable {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(2, 0, 2, 14, 8, 14), Block.createCuboidShape(0, 8, 0, 16, 16, 16), BooleanBiFunction.OR);

    protected DrumBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(POWERED, false));
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (hit.getSide() == Direction.UP) {
            BlockPos pos = hit.getBlockPos();
            world.setBlockState(pos, state.with(POWERED, true), 3);
            world.createAndScheduleBlockTick(pos, this, this.getPressTicks());
            world.emitGameEvent(projectile.getOwner(), GameEvent.BLOCK_CHANGE, pos);
            int power = calculatePower(hit.getPos());
            if (power >= 1 && 5 >= power) this.playDrumSound(world, pos, "high");
            if (power > 5 && 11 >= power) this.playDrumSound(world, pos, "medium");
            if (power > 11) this.playDrumSound(world, pos, "low");
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide() == Direction.UP && world.getBlockState(pos.up()).isAir()) {
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            world.setBlockState(pos, state.with(POWERED, true), 3);
            world.createAndScheduleBlockTick(pos, this, this.getPressTicks());
            int power = calculatePower(hit.getPos());
            if (power >= 1 && 5 >= power) this.playDrumSound(world, pos, "high");
            if (power > 5 && 11 >= power) this.playDrumSound(world, pos, "medium");
            if (power > 11) this.playDrumSound(world, pos, "low");
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private static int calculatePower(Vec3d pos) { return Math.max(1, MathHelper.ceil(15.0 * MathHelper.clamp((0.5 - Math.max(Math.abs(MathHelper.fractionalPart(pos.x) - 0.5), Math.abs(MathHelper.fractionalPart(pos.z) - 0.5))) / 0.5, 0.0, 1.0))); }

    public void playDrumSound(World world, BlockPos pos, String string) {
        if (string.matches("high")) {
            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 16 / 24.0, 0.0, 0.0);
            if (world.getBlockState(pos.down()).isOf(Blocks.GOLD_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_HIGH, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.PUMPKIN)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_HIGH, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.SAND)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_SAND_DRUM_HIGH, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.IRON_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_HIGH, SoundCategory.RECORDS, 1, 1);
            else world.playSound(null, pos, EtceteraSoundEvents.BLOCK_DRUM_HIGH, SoundCategory.RECORDS, 1, 1);
        }
        if (string.matches("medium")) {
            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 8 / 24.0, 0.0, 0.0);
            if (world.getBlockState(pos.down()).isOf(Blocks.GOLD_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_MEDIUM, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.PUMPKIN)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_MEDIUM, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.SAND)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_SAND_DRUM_MEDIUM, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.IRON_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_MEDIUM, SoundCategory.RECORDS, 1, 1);
            else world.playSound(null, pos, EtceteraSoundEvents.BLOCK_DRUM_MEDIUM, SoundCategory.RECORDS, 1, 1);
        }
        if (string.matches("low")) {
            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 1 / 24.0, 0.0, 0.0);
            if (world.getBlockState(pos.down()).isOf(Blocks.GOLD_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_LOW, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.PUMPKIN)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_LOW, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.SAND)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_SAND_DRUM_LOW, SoundCategory.RECORDS, 1, 1);
            else if (world.getBlockState(pos.down()).isOf(Blocks.IRON_BLOCK)) world.playSound(null, pos, EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_LOW, SoundCategory.RECORDS, 1, 1);
            else world.playSound(null, pos, EtceteraSoundEvents.BLOCK_DRUM_LOW, SoundCategory.RECORDS, 1, 1);
        }
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }


    private int getPressTicks() { return 3; }
    @Override public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) { if (state.get(POWERED)) world.setBlockState(pos, state.with(POWERED, false), 3); }
    @Override public boolean emitsRedstonePower(BlockState state) { return true; }
    @Override public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) { return state.get(POWERED) ? 3 : 0; }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override public BlockState getPlacementState(ItemPlacementContext ctx) { return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER); }
    @Override public FluidState getFluidState(BlockState state) { return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state); }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(WATERLOGGED, POWERED); }
}
