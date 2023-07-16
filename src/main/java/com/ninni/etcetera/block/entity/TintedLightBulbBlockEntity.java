package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.block.AbstractLightBulbBlock;
import com.ninni.etcetera.block.TintedLightBulbBlock;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TintedLightBulbBlockEntity extends BlockEntity {
    private int ticksBeforeFlicker;
    private int offTicks;
    private String name;

    public TintedLightBulbBlockEntity(BlockPos pos, BlockState state) {
        super(EtceteraBlockEntityType.TINTED_LIGHT_BULB.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.ticksBeforeFlicker = nbt.getInt("ticks_before_flicker");
        this.offTicks = nbt.getInt("ticks_before_flicker");
        this.name = nbt.getString("brightness");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("ticks_before_flicker", this.ticksBeforeFlicker);
        nbt.putInt("off_ticks", this.offTicks);
        nbt.putString("brightness", this.name);
    }

    public void setTicksBeforeFlicker(int ticksBeforeFlicker) {
        this.ticksBeforeFlicker = ticksBeforeFlicker;
    }

    public void tick(Level world, BlockPos pos, BlockState state, TintedLightBulbBlockEntity tintedLightBulbBlockEntity) {
        if (this.offTicks > 1) {
            this.offTicks--;
            return;
        }
        if (this.offTicks == 1 && state.getValue(AbstractLightBulbBlock.BRIGHTNESS) == LightBulbBrightness.OFF && this.name != null) {
            world.setBlockAndUpdate(pos, state.setValue(AbstractLightBulbBlock.BRIGHTNESS, LightBulbBrightness.valueOf(this.name)));
            world.playSound(null, pos, EtceteraSoundEvents.BLOCK_LIGHT_BULB_ON.get(), SoundSource.BLOCKS, 0.1F, 0.25F);
            tintedLightBulbBlockEntity.setTicksBeforeFlicker(((TintedLightBulbBlock)state.getBlock()).getTicksBeforeFlicker(world));
            this.offTicks = 0;
        }
        if (this.offTicks != 0 && state.getValue(AbstractLightBulbBlock.BRIGHTNESS) == LightBulbBrightness.OFF && tintedLightBulbBlockEntity.ticksBeforeFlicker > 0) {
            this.setTicksBeforeFlicker(0);
        }
        if (tintedLightBulbBlockEntity.ticksBeforeFlicker > 0) {
            this.setTicksBeforeFlicker(this.ticksBeforeFlicker - 1);
        } else if (state.getValue(AbstractLightBulbBlock.BRIGHTNESS) != LightBulbBrightness.OFF){
            this.name = state.getValue(AbstractLightBulbBlock.BRIGHTNESS).name();
            world.scheduleTick(pos, state.getBlock(), 2);
            world.setBlockAndUpdate(pos, state.setValue(AbstractLightBulbBlock.BRIGHTNESS, LightBulbBrightness.OFF));
            world.playSound(null, pos, EtceteraSoundEvents.BLOCK_LIGHT_BULB_OFF.get(), SoundSource.BLOCKS, 0.1F, 0.25F);
            this.offTicks = Mth.nextInt(world.random, 1, 30);
        }
    }
}
