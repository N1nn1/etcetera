package com.ninni.etcetera.block;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Random;

public class CrumblingStoneBlock extends Block {
    public static final IntProperty LEVEL = Properties.LEVEL_3;

    public CrumblingStoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        this.tryBreakStone(world, state, pos, 25);
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        this.tryBreakStone(world, state, pos, 1);
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    private void tryBreakStone(World world, BlockState state, BlockPos pos, int inverseChance) { if (!world.isClient && world.random.nextInt(inverseChance) == 0 && state.isOf(EtceteraBlocks.CRUMBLING_STONE)) { this.breakStone(world, pos, state); } }

    private void breakStone(World world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, EtceteraSoundEvents.BLOCK_CRUMBLING_STONE_CRUMBLE, SoundCategory.BLOCKS, 0.7f, 0.9f + world.random.nextFloat() * 0.2f);
        int i = state.get(LEVEL);
        if (i >= 3) { world.breakBlock(pos, false); }
        else {
            world.setBlockState(pos, state.with(LEVEL, i + 1), Block.NOTIFY_LISTENERS);
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }
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
