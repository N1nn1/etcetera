package com.ninni.etcetera.block;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.block.entity.ItemStandBlockEntity;
import com.ninni.etcetera.stat.EtceteraStats;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
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


@SuppressWarnings("deprecation")
public class ItemStandBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty GLASS = EtceteraProperties.GLASS;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(1, 0, 1, 15, 2, 15), Block.createCuboidShape(7, 2, 7, 9, 7, 9), BooleanBiFunction.OR);

    protected ItemStandBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(GLASS, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        ItemStack itemStack = player.getStackInHand(hand);

        if (blockEntity instanceof ItemStandBlockEntity itemStandBlockEntity) {
            if (!world.isClient && itemStandBlockEntity.addItem(player, player.getAbilities().creativeMode ? itemStack.copy() : itemStack) && !itemStack.isEmpty()) {
                player.incrementStat(EtceteraStats.INTERACT_WITH_ITEM_STAND);
                world.playSound(null, pos, SoundEvents.ENTITY_GLOW_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1, 1);
                return ActionResult.SUCCESS;
            }
            if (itemStack.isOf(Blocks.GLASS.asItem()) && !itemStandBlockEntity.addItem(player, player.getAbilities().creativeMode ? itemStack.copy() : itemStack) && !state.get(GLASS)) {
                world.setBlockState(pos, state.with(GLASS, true));
                world.playSound(null, pos, Blocks.GLASS.getSoundGroup(Blocks.GLASS.getDefaultState()).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
                if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemStandBlockEntity) {
            ItemScatterer.spawn(world, pos, ((ItemStandBlockEntity)blockEntity).getItemsDisplayed());
        }
        if (state.get(GLASS)) ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Blocks.GLASS.asItem().getDefaultStack());


        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(GLASS) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return state.get(GLASS) ? BlockSoundGroup.GLASS : BlockSoundGroup.STONE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemStandBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER).with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, GLASS);
    }
}
