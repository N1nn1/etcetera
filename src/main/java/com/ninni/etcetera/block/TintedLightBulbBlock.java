package com.ninni.etcetera.block;

import com.ninni.etcetera.block.entity.TintedLightBulbBlockEntity;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TintedLightBulbBlock extends AbstractLightBulbBlock implements EntityBlock {

    public TintedLightBulbBlock(Properties settings) {
        super(settings.lightLevel(TintedLightBulbBlock::getLuminance));
    }

    public static int getLuminance(BlockState state) {
        LightBulbBrightness brightness = state.getValue(BRIGHTNESS);

        if (brightness == LightBulbBrightness.DARK) return 3;
        else if (brightness == LightBulbBrightness.DIM) return 6;
        else if (brightness == LightBulbBrightness.BRIGHT) return 10;
        return 0;
    }

    @Override
    public void turnBrightness(BlockState state, Level world, BlockPos pos, LightBulbBrightness brightness) {
        super.turnBrightness(state, world, pos, brightness);
        if (world.getBlockEntity(pos) instanceof TintedLightBulbBlockEntity tintedLightBulbBlockEntity && state.getValue(BRIGHTNESS) == LightBulbBrightness.OFF) {
            tintedLightBulbBlockEntity.setTicksBeforeFlicker(this.getTicksBeforeFlicker(world));
        }
    }

    public int getTicksBeforeFlicker(Level world) {
        return UniformInt.of(200, 1000).sample(world.getRandom());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TintedLightBulbBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world2, BlockState p_153213_, BlockEntityType<T> type) {
        return !world2.isClientSide && EtceteraBlockEntityType.TINTED_LIGHT_BULB.get() == type ? (world, pos, state1, blockEntity) -> {
            TintedLightBulbBlockEntity tintedLightBulbBlockEntity = (TintedLightBulbBlockEntity) blockEntity;
            tintedLightBulbBlockEntity.tick(world, pos, state1, tintedLightBulbBlockEntity);
        } : null;
    }

}
