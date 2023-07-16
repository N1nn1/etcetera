package com.ninni.etcetera.block;

import com.ninni.etcetera.block.entity.ItemStandBlockEntity;
import com.ninni.etcetera.registry.EtceteraProperties;
import com.ninni.etcetera.registry.EtceteraStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("deprecation")
public class ItemStandBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty GLASS = EtceteraProperties.GLASS;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Shapes.join(Block.box(1, 0, 1, 15, 2, 15), Block.box(7, 2, 7, 9, 7, 9), BooleanOp.OR);

    public ItemStandBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(GLASS, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        ItemStack itemStack = player.getItemInHand(hand);

        if (blockEntity instanceof ItemStandBlockEntity itemStandBlockEntity) {
            if (!world.isClientSide && itemStandBlockEntity.addItem(player, player.getAbilities().instabuild ? itemStack.copy() : itemStack) && !itemStack.isEmpty()) {
                player.awardStat(EtceteraStats.INTERACT_WITH_ITEM_STAND.get());
                world.playSound(null, pos, SoundEvents.GLOW_ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            }
            if (itemStack.is(Blocks.GLASS.asItem()) && !itemStandBlockEntity.addItem(player, player.getAbilities().instabuild ? itemStack.copy() : itemStack) && !state.getValue(GLASS)) {
                world.setBlockAndUpdate(pos, state.setValue(GLASS, true));
                world.playSound(null, pos, Blocks.GLASS.getSoundType(Blocks.GLASS.defaultBlockState()).getPlaceSound(), SoundSource.BLOCKS, 1, 1);
                if (!player.getAbilities().instabuild) itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock())) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemStandBlockEntity itemStandBlockEntity) {
            Containers.dropContents(world, pos, itemStandBlockEntity.getItemsDisplayed());
        }
        if (state.getValue(GLASS)) Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Blocks.GLASS.asItem().getDefaultInstance());


        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(GLASS) ? Shapes.block() : SHAPE;
    }

    @Override
    public SoundType getSoundType(BlockState state) {
        return state.getValue(GLASS) ? SoundType.GLASS : SoundType.STONE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemStandBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER).setValue(FACING, ctx.getNearestLookingDirection());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, GLASS);
    }

}
