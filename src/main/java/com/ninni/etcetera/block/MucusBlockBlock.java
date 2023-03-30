package com.ninni.etcetera.block;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.entity.EtceteraEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
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
public class MucusBlockBlock extends Block {
    private static final IntProperty DISTANCE = Properties.DISTANCE_1_7;
    public static final BooleanProperty SOLID = EtceteraProperties.SOLID;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1, 1, 1, 15, 10, 15);

    public MucusBlockBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SOLID, false).with(DISTANCE, 7));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateDistanceFromWater(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i;
        if ((i = getDistanceFromLog(neighborState) + 1) != 1 || state.get(DISTANCE) != i) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromWater(state, world, pos), Block.NOTIFY_ALL);
    }

    private static BlockState updateDistanceFromWater(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (i == 1) break;
        }
        return state.with(DISTANCE, i).with(SOLID, i < 7);
    }

    private static int getDistanceFromLog(BlockState state) {
        if (state.isOf(Blocks.WATER)) {
            return 0;
        }
        if (state.isOf(EtceteraBlocks.MUCUS_BLOCK)) {
            return state.get(DISTANCE);
        }
        return 7;
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
        builder.add(SOLID, DISTANCE);
    }
}
