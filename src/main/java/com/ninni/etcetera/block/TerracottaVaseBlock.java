package com.ninni.etcetera.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class TerracottaVaseBlock extends Block {
    protected static final VoxelShape SHAPE =
    Stream.of(
        Block.createCuboidShape(0, 0, 0, 16, 11, 16),
        Block.createCuboidShape(2, 11, 2, 14, 13, 14),
        Block.createCuboidShape(0, 13, 0, 16, 16, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public TerracottaVaseBlock(Settings settings) { super(settings); }

    @Override public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
}
