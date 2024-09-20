package com.ninni.etcetera.block;

import com.ninni.etcetera.registry.EtceteraProperties;
import com.ninni.etcetera.block.enums.DrumType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
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
import net.minecraft.state.property.EnumProperty;
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

    public static final EnumProperty<DrumType> TYPE = EtceteraProperties.DRUM_TYPE;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(2, 0, 2, 14, 8, 14), Block.createCuboidShape(0, 8, 0, 16, 16, 16), BooleanBiFunction.OR);
    protected static final VoxelShape HIT_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(-0.25, 8, -0.25, 16.25, 15.75, 16.25), Block.createCuboidShape(2, 0, 2, 14, 8, 14), BooleanBiFunction.OR);

    public DrumBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(WATERLOGGED, false).with(POWERED, false).with(TYPE, DrumType.DJEMBE));
    }

    public void playDrumSound(BlockState state, World world, BlockPos pos, Entity player, boolean isHit, Vec3d hit, int signal) {
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        world.setBlockState(pos, state.with(POWERED, true), 3);
        world.scheduleBlockTick(pos, this, getPressTicks());
        if (isHit) {
            int power = calculatePower(hit);
            if (power >= 1 && 5 >= power) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 16 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getHighSound(), SoundCategory.RECORDS, 2, 1);
            }
            if (power > 5 && 11 >= power) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 8 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getMediumSound(), SoundCategory.RECORDS, 2, 1);
            }
            if (power > 11) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 1 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getLowSound(), SoundCategory.RECORDS, 2, 1);
            }
        } else {
            if (signal == 15) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 16 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getHighSound(), SoundCategory.RECORDS, 2, 1);
            }
            if (signal == 14) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 8 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getMediumSound(), SoundCategory.RECORDS, 2, 1);
            }
            if (signal < 14) {
                world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 1 / 24.0, 0.0, 0.0);
                world.playSound(null, pos, getType(state).getLowSound(), SoundCategory.RECORDS, 2, 1);
            }
        }
    }

    private int getPressTicks() {
        return 6;
    }

    private DrumType getType(BlockState state) {
        return state.get(TYPE);
    }

    private int calculatePower(Vec3d pos) {
        return Math.max(1, MathHelper.ceil(15.0 * MathHelper.clamp((0.5 - Math.max(Math.abs(MathHelper.fractionalPart(pos.x) - 0.5), Math.abs(MathHelper.fractionalPart(pos.z) - 0.5))) / 0.5, 0.0, 1.0)));
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (hit.getSide() == Direction.UP) playDrumSound(state, world, hit.getBlockPos(), projectile.getOwner(), true, hit.getPos(), 0);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        playDrumSound(state, world, pos, entity, true, entity.getPos(), 0);
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide() == Direction.UP && world.getBlockState(pos.up()).isAir()) {
            playDrumSound(state, world, pos, player, true, hit.getPos(), 0);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(POWERED) ? HIT_SHAPE : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) world.setBlockState(pos, state.with(POWERED, false), 3);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            if (bl) {
                playDrumSound(state, world, pos, null, false, Vec3d.ZERO, world.getReceivedRedstonePower(pos));
            }
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return direction == Direction.DOWN ? state.with(TYPE, DrumType.fromBlockState(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(TYPE, DrumType.fromBlockState(ctx.getWorld().getBlockState(ctx.getBlockPos().down())));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, POWERED, TYPE);
    }
}
