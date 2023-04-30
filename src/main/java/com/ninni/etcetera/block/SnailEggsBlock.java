package com.ninni.etcetera.block;

import com.ninni.etcetera.entity.EtceteraEntityType;
import com.ninni.etcetera.entity.SnailEntity;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class SnailEggsBlock extends MultifaceGrowthBlock {
    private final LichenGrower grower = new LichenGrower(this);

    public SnailEggsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public LichenGrower getGrower() {
        return this.grower;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(EtceteraItems.SNAIL_EGGS.asItem()) || super.canReplace(state, context);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, getHatchTime(world.getRandom()));
    }

    private static int getHatchTime(Random random) {
        return random.nextBetweenExclusive(6000, 12000);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.breakBlock(pos, false);
        world.playSound(null, pos, EtceteraSoundEvents.BLOCK_SNAIL_EGGS_HATCH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        this.spawnSnails(world, pos, random);
    }

    private void spawnSnails(ServerWorld world, BlockPos pos, Random random) {
        int i = random.nextBetweenExclusive(2, 4);
        for (int j = 1; j <= i; ++j) {
            SnailEntity snail = EtceteraEntityType.SNAIL.create(world);
            double d = (double)pos.getX() + this.getSpawnOffset(random);
            double e = (double)pos.getZ() + this.getSpawnOffset(random);
            int k = random.nextBetweenExclusive(1, 361);
            snail.refreshPositionAndAngles(d, pos.getY(), e, k, 0.0f);
            snail.setPersistent();
            snail.setBaby(true);
            world.spawnEntity(snail);
        }
    }

    private double getSpawnOffset(Random random) {
        double d = 0.4F;
        return MathHelper.clamp(random.nextDouble(), d, 1.0 - d);
    }

}
