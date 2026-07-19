package com.ninni.etcetera.mixin;

import com.ninni.etcetera.block.RedstoneWiresBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings({"AddedMixinMembersNamePattern", "MissingUnique", "unused"})
@Mixin(RedstoneWiresBlock.class)
public abstract class NeoRedstoneWiresBlockMixin implements IBlockExtension {

    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return false;
    }
}
