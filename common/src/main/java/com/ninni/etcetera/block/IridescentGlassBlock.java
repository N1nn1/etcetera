package com.ninni.etcetera.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IridescentGlassBlock extends TransparentBlock implements BeaconBeamBlock {
    public static final ThreadLocal<Boolean> IS_RAINBOW = ThreadLocal.withInitial(() -> false);

    public IridescentGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull DyeColor getColor() {
        IS_RAINBOW.set(true);
        return DyeColor.WHITE;
    }

    public Integer getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        return -1;
    }
}