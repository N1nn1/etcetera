package com.ninni.etcetera.block;

import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.block.*;

public class LightBulbBlock extends AbstractLightBulbBlock {

    public LightBulbBlock(Settings settings) {
        super(settings.luminance(LightBulbBlock::getLuminance));
    }

    public static int getLuminance(BlockState state) {
        LightBulbBrightness brightness = state.get(BRIGHTNESS);

        if (brightness == LightBulbBrightness.DARK) return 5;
        else if (brightness == LightBulbBrightness.DIM) return 10;
        else if (brightness == LightBulbBrightness.BRIGHT) return 15;
        return 0;
    }
}
