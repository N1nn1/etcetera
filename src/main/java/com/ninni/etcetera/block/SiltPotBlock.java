package com.ninni.etcetera.block;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.block.entity.EtceteraBlockEntityType;
import com.ninni.etcetera.block.entity.SiltPotBlockEntity;
import com.ninni.etcetera.stat.EtceteraStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class SiltPotBlock extends FallingBlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty FILLED = EtceteraProperties.FILLED;
    protected static final VoxelShape SHAPE =
        Stream.of(
            Block.createCuboidShape(2, 1, 2, 14, 13, 14),
            Block.createCuboidShape(3, 14, 3, 13, 16, 13),
            Block.createCuboidShape(4, 0, 4, 12, 14, 12)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public SiltPotBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FILLED, false));
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        ItemStack stack = player.getStackInHand(hand);
        if (hit.getSide() == Direction.UP && stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock && state.get(FILLED)) {
            blockItem.place(new ItemPlacementContext(player, hand, stack, hit));
            return ActionResult.success(world.isClient);
        }
        if (state.get(FILLED) && player.getStackInHand(hand).getItem() instanceof ShovelItem) {
            world.setBlockState(pos, state.with(FILLED, false), Block.NO_REDRAW);
            if (!player.getAbilities().creativeMode) player.getStackInHand(hand).damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
            if (world.isClient) {
                world.playSound(player, pos, SoundEvents.BLOCK_ROOTED_DIRT_BREAK, SoundCategory.BLOCKS, 1, 1);
                return ActionResult.SUCCESS;
            }
        } else if (!state.get(FILLED) && player.getStackInHand(hand).isOf(Blocks.ROOTED_DIRT.asItem())) {
            world.setBlockState(pos, state.with(FILLED, true), Block.NO_REDRAW);
            if (world.isClient) {
                world.playSound(player, pos, SoundEvents.BLOCK_ROOTED_DIRT_PLACE, SoundCategory.BLOCKS, 1, 1);
                return ActionResult.SUCCESS;
            }
            if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(1);
        } else if (blockEntity instanceof SiltPotBlockEntity && !state.get(FILLED)) {
            if (world.isClient) return ActionResult.SUCCESS;
            player.openHandledScreen((SiltPotBlockEntity)blockEntity);
            player.incrementStat(EtceteraStats.OPEN_SILT_POT);
            PiglinBrain.onGuardedBlockInteracted(player, true);
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        super.configureFallingBlockEntity(entity);
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        SiltPotBlockEntity blockEntity = EtceteraBlockEntityType.SILT_POT.instantiate(pos, fallingBlockEntity.getBlockState());
        blockEntity.readNbt(fallingBlockEntity.blockEntityData);
        ItemScatterer.spawn(world, pos, blockEntity);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) return;
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!moved && blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)(blockEntity));
            world.updateComparators(pos, this);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SiltPotBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomName() && (blockEntity = world.getBlockEntity(pos)) instanceof SiltPotBlockEntity) {
            ((SiltPotBlockEntity)blockEntity).setCustomName(itemStack.getName());
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FILLED);
    }
}
