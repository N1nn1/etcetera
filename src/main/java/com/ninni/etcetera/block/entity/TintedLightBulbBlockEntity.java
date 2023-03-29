package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.block.AbstractLightBulbBlock;
import com.ninni.etcetera.block.TintedLightBulbBlock;
import com.ninni.etcetera.block.enums.LightBulbBrightness;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TintedLightBulbBlockEntity extends BlockEntity {
    private int ticksBeforeFlicker;
    private int offTicks;
    private String name;

    public TintedLightBulbBlockEntity(BlockPos pos, BlockState state) {
        super(EtceteraBlockEntityType.TINTED_LIGHT_BULB, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.ticksBeforeFlicker = nbt.getInt("ticks_before_flicker");
        this.offTicks = nbt.getInt("ticks_before_flicker");
        this.name = nbt.getString("brightness");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("ticks_before_flicker", this.ticksBeforeFlicker);
        nbt.putInt("off_ticks", this.offTicks);
        nbt.putString("brightness", this.name);
    }

    public void setTicksBeforeFlicker(int ticksBeforeFlicker) {
        this.ticksBeforeFlicker = ticksBeforeFlicker;
    }

    public void tick(World world, BlockPos pos, BlockState state, TintedLightBulbBlockEntity tintedLightBulbBlockEntity) {
        if (this.offTicks > 1) {
            this.offTicks--;
            return;
        }
        if (this.offTicks == 1 && state.get(AbstractLightBulbBlock.BRIGHTNESS) == LightBulbBrightness.OFF && this.name != null) {
            world.setBlockState(pos, state.with(AbstractLightBulbBlock.BRIGHTNESS, LightBulbBrightness.valueOf(this.name)));
            world.playSound(null, pos, EtceteraSoundEvents.BLOCK_LIGHT_BULB_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
            tintedLightBulbBlockEntity.setTicksBeforeFlicker(((TintedLightBulbBlock)state.getBlock()).getTicksBeforeFlicker(world));
            this.offTicks = 0;
        }
        if (this.offTicks != 0 && state.get(AbstractLightBulbBlock.BRIGHTNESS) == LightBulbBrightness.OFF && tintedLightBulbBlockEntity.ticksBeforeFlicker > 0) {
            this.setTicksBeforeFlicker(0);
        }
        if (tintedLightBulbBlockEntity.ticksBeforeFlicker > 0) {
            this.setTicksBeforeFlicker(this.ticksBeforeFlicker - 1);
        } else if (state.get(AbstractLightBulbBlock.BRIGHTNESS) != LightBulbBrightness.OFF){
            this.name = state.get(AbstractLightBulbBlock.BRIGHTNESS).name();
            world.createAndScheduleBlockTick(pos, state.getBlock(), 2);
            world.setBlockState(pos, state.with(AbstractLightBulbBlock.BRIGHTNESS, LightBulbBrightness.OFF));
            world.playSound(null, pos, EtceteraSoundEvents.BLOCK_LIGHT_BULB_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.offTicks = 5;
        }
    }
}
