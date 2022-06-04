package com.ninni.etcetera.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WaxedCrumblingStoneBlock extends Block {
    public static final IntProperty LEVEL = Properties.LEVEL_3;

    public WaxedCrumblingStoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0 && state.get(LEVEL) >= 2) {
            double x = (double)pos.getX() + random.nextDouble();
            double y = (double)pos.getY() - 0.05;
            double z = (double)pos.getZ() + random.nextDouble();
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(LEVEL); }
}
