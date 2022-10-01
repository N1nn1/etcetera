package com.ninni.etcetera.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityInvoker {
    @Invoker("<init>")
    static FallingBlockEntity invokeInit(World world, double x, double y, double z, BlockState block) {throw new UnsupportedOperationException();}
}
