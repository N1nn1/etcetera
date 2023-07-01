package com.ninni.etcetera.block;

import com.ninni.etcetera.block.entity.EtceteraBlockEntityType;
import com.ninni.etcetera.block.entity.TintedLightBulbBlockEntity;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TintedLightBulbBlock extends AbstractLightBulbBlock implements BlockEntityProvider {

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

    @Override
    public void turnBrightness(BlockState state, World world, BlockPos pos, LightBulbBrightness brightness) {
        super.turnBrightness(state, world, pos, brightness);
        if (world.getBlockEntity(pos) instanceof TintedLightBulbBlockEntity tintedLightBulbBlockEntity && state.get(BRIGHTNESS) == LightBulbBrightness.OFF) {
            tintedLightBulbBlockEntity.setTicksBeforeFlicker(this.getTicksBeforeFlicker(world));
        }
    }

    public int getTicksBeforeFlicker(World world) {
        return UniformIntProvider.create(200, 1000).get(world.getRandom());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TintedLightBulbBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world2, BlockState state, BlockEntityType<T> type) {
        return !world2.isClient && EtceteraBlockEntityType.TINTED_LIGHT_BULB == type ? (world, pos, state1, blockEntity) -> {
            TintedLightBulbBlockEntity tintedLightBulbBlockEntity = (TintedLightBulbBlockEntity) blockEntity;
            tintedLightBulbBlockEntity.tick(world, pos, state1, tintedLightBulbBlockEntity);
        } : null;
    }
}
