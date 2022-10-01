package com.ninni.etcetera.mixin;

import com.ninni.etcetera.EtceteraProperties;
import com.ninni.etcetera.EtceteraTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlowerBlock.class)
public abstract class FlowerBlockMixin extends PlantBlock {
    public FlowerBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return (floor.isIn(EtceteraTags.SILT_POTS_BLOCK) && floor.get(EtceteraProperties.FILLED)) || super.canPlantOnTop(floor, world, pos);
    }
}
