package com.ninni.etcetera.block;

import com.mojang.serialization.MapCodec;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LightBulbBlock extends AbstractLightBulbBlock {

    public static final MapCodec<LightBulbBlock> CODEC = simpleCodec(LightBulbBlock::new);

    public LightBulbBlock(Properties properties) {
        super(properties.lightLevel(LightBulbBlock::getLuminance));
    }

    public static int getLuminance(BlockState state) {
        LightBulbBrightness brightness = state.getValue(BRIGHTNESS);

        if (brightness == LightBulbBrightness.DARK) return 5;
        else if (brightness == LightBulbBrightness.DIM) return 10;
        else if (brightness == LightBulbBrightness.BRIGHT) return 15;
        return 0;
    }

    @Override
    protected @NotNull MapCodec<? extends AbstractLightBulbBlock> codec() {
        return CODEC;
    }
}