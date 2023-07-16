package com.ninni.etcetera.block;

import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.world.level.block.state.BlockState;

public class LightBulbBlock extends AbstractLightBulbBlock {

    public LightBulbBlock(Properties settings) {
        super(settings.lightLevel(LightBulbBlock::getLuminance));
    }

    public static int getLuminance(BlockState state) {
        LightBulbBrightness brightness = state.getValue(BRIGHTNESS);

        if (brightness == LightBulbBrightness.DARK) return 5;
        else if (brightness == LightBulbBrightness.DIM) return 10;
        else if (brightness == LightBulbBrightness.BRIGHT) return 15;
        return 0;
    }
}
