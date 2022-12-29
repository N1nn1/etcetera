package com.ninni.etcetera.block;

import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.block.BlockState;
public class TintedLightBulbBlock extends AbstractLightBulbBlock {

    public TintedLightBulbBlock(Settings settings) {
        super(settings.luminance(TintedLightBulbBlock::getLuminance));
    }

    public static int getLuminance(BlockState state) {
        LightBulbBrightness brightness = state.get(BRIGHTNESS);

        if (brightness == LightBulbBrightness.DARK) return 3;
        else if (brightness == LightBulbBrightness.DIM) return 6;
        else if (brightness == LightBulbBrightness.BRIGHT) return 10;
        return 0;
    }
}
