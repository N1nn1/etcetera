package com.ninni.etcetera.block;

import com.ninni.etcetera.block.enums.DrumType;
import com.ninni.etcetera.registry.EtceteraProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class DrumBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<DrumType> TYPE = EtceteraProperties.DRUM_TYPE;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Shapes.join(Block.box(2, 0, 2, 14, 8, 14), Block.box(0, 8, 0, 16, 16, 16), BooleanOp.OR);
    protected static final VoxelShape HIT_SHAPE = Shapes.join(Block.box(-0.25, 8, -0.25, 16.25, 15.75, 16.25), Block.box(2, 0, 2, 14, 8, 14), BooleanOp.OR);

    public DrumBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(POWERED, false).setValue(TYPE, DrumType.DJEMBE));
    }

    public void playDrumSound(BlockState state, Level world, BlockPos pos, Entity player, Vec3 hit) {
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        world.setBlock(pos, state.setValue(POWERED, true), 3);
        world.scheduleTick(pos, this, getPressTicks());
        int power = calculatePower(hit);
//        if (power >= 1 && 5 >= power) {
//            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 16 / 24.0, 0.0, 0.0);
//            world.playSound(null, pos, getType(state).getHighSound().get(), SoundSource.RECORDS, 2, 1);
//        }
//        if (power > 5 && 11 >= power) {
//            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 8 / 24.0, 0.0, 0.0);
//            world.playSound(null, pos, getType(state).getMediumSound().get(), SoundSource.RECORDS, 2, 1);
//        }
//        if (power > 11) {
//            world.addParticle(ParticleTypes.NOTE, (double) pos.getX() + 0.5, (double) pos.getY() + 1.2, (double) pos.getZ() + 0.5, 1 / 24.0, 0.0, 0.0);
//            world.playSound(null, pos, getType(state).getLowSound().get(), SoundSource.RECORDS, 2, 1);
//        }
    }

    private int getPressTicks() {
        return 3;
    }

    private DrumType getType(BlockState state) {
        return state.getValue(TYPE);
    }

    private int calculatePower(Vec3 pos) {
        return Math.max(1, Mth.ceil(15.0 * Mth.clamp((0.5 - Math.max(Math.abs(Mth.frac(pos.x) - 0.5), Math.abs(Mth.frac(pos.z) - 0.5))) / 0.5, 0.0, 1.0)));
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (hit.getDirection() == Direction.UP) playDrumSound(state, world, hit.getBlockPos(), projectile.getOwner(), hit.getLocation());
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        playDrumSound(state, world, pos, entity, entity.position());
        super.fallOn(world, state, pos, entity, fallDistance);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult hit) {
        if (hit.getDirection() == Direction.UP && world.getBlockState(pos.above()).isAir()) {
            playDrumSound(state, world, pos, player, hit.getLocation());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos blockPos, CollisionContext context) {
        return state.getValue(POWERED) ? HIT_SHAPE : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource p_222948_) {
        if (state.getValue(POWERED)) world.setBlock(pos, state.setValue(POWERED, false), 3);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) ? 3 : 0;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return direction == Direction.DOWN ? state.setValue(TYPE, DrumType.fromBlockState(neighborState)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER).setValue(TYPE, DrumType.fromBlockState(ctx.getLevel().getBlockState(ctx.getClickedPos().below())));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, POWERED, TYPE);
    }
}
