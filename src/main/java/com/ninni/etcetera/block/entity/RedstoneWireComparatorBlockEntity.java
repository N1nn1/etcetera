package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class RedstoneWireComparatorBlockEntity extends BlockEntity {
    private int outputSignal;

    public RedstoneWireComparatorBlockEntity(BlockPos pos, BlockState state) {
        super(EtceteraBlockEntityType.REDSTONE_WIRE_COMPARATOR, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("OutputSignal", this.outputSignal);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.outputSignal = nbt.getInt("OutputSignal");
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public void setOutputSignal(int outputSignal) {
        this.outputSignal = outputSignal;
    }
}

