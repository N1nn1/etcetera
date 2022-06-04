package com.ninni.etcetera.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class BouquetBlock extends Block {
    public BouquetBlock(Settings settings) { super(settings); }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) { return false; }
}
