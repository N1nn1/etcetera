package com.ninni.etcetera.block;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class CrumblingStoneBlock extends AbstractCrumblingStoneBlock {

    public CrumblingStoneBlock(Settings settings) { super(settings); }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        this.tryBreakStone(world, state, hit.getBlockPos(), 1);
        super.onProjectileHit(world, state, hit, projectile);
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
}
