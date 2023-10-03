package com.ninni.etcetera.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamCatcherItem extends BlockItem {
    public DreamCatcherItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos().down();
        BlockState blockState = world.isWater(blockPos) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
        world.setBlockState(blockPos, blockState, 27);
        return super.place(context, state);
    }
}
