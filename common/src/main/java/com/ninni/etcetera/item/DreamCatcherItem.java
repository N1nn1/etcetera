package com.ninni.etcetera.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DreamCatcherItem extends BlockItem {
    public DreamCatcherItem(Block block, Item.Properties settings) {
        super(block, settings);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, @NotNull BlockState state) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos().below();
        BlockState blockState = world.getFluidState(blockPos).is(FluidTags.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        world.setBlock(blockPos, blockState, 27);
        return super.placeBlock(context, state);
    }
}