package com.ninni.etcetera.block;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.entity.EtceteraEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class MucusBlock extends Block {
    public static final BooleanProperty SOLID = EtceteraProperties.SOLID;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1, 1, 1, 15, 10, 15);

    public MucusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SOLID, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (solidifiesOnAnySide(world, blockPos)) {
            return this.getDefaultState().with(SOLID, true);
        } else {
            world.createAndScheduleBlockTick(ctx.getBlockPos(), this, 16);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (solidifiesOnAnySide(world, pos) && !state.get(SOLID)) {
            return this.getDefaultState().with(SOLID, true);
        }
        world.createAndScheduleBlockTick(pos, this, 16);
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!solidifiesOnAnySide(world, pos) && state.get(SOLID)) {
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1);
            world.setBlockState(pos, state.with(SOLID, false));
        }
    }

    private static boolean solidifiesOnAnySide(BlockView world, BlockPos pos) {
        return solidifiesIn(world.getBlockState(pos.down())) || solidifiesIn(world.getBlockState(pos.up())) || solidifiesIn(world.getBlockState(pos.south())) || solidifiesIn(world.getBlockState(pos.north())) || solidifiesIn(world.getBlockState(pos.west())) || solidifiesIn(world.getBlockState(pos.east()));
    }

    private static boolean solidifiesIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getType() == EtceteraEntityType.SNAIL || entity.getType() == EntityType.BEE) {
            return;
        }
        entity.slowMovement(state, new Vec3d(0.5f, 0.5, 0.5f));
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(SOLID) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SOLID);
    }
}
