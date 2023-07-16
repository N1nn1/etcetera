package com.ninni.etcetera.block;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CrumblingStoneBlock extends AbstractCrumblingStoneBlock {

    public CrumblingStoneBlock(Properties properties) { super(properties); }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        this.tryBreakStone(world, state, hit.getBlockPos(), 1);
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        this.tryBreakStone(world, state, pos, 25);
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        this.tryBreakStone(world, state, pos, 1);
        super.fallOn(world, state, pos, entity, fallDistance);
    }

    private void tryBreakStone(Level world, BlockState state, BlockPos pos, int inverseChance) { if (!world.isClientSide && world.random.nextInt(inverseChance) == 0 && state.is(EtceteraBlocks.CRUMBLING_STONE.get())) { this.breakStone(world, pos, state); } }

    private void breakStone(Level world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, EtceteraSoundEvents.BLOCK_CRUMBLING_STONE_CRUMBLE.get(), SoundSource.BLOCKS, 0.7f, 0.9f + world.random.nextFloat() * 0.2f);
        int i = state.getValue(LEVEL);
        if (i >= 3) { world.destroyBlock(pos, false); }
        else {
            world.setBlock(pos, state.setValue(LEVEL, i + 1), 2);
            world.levelEvent(2001, pos, Block.getId(state));
        }
    }
}
