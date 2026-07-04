package com.ninni.etcetera.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.TransparentBlock;
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
}