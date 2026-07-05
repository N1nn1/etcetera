package com.ninni.etcetera.mixin;

import com.ninni.etcetera.block.RedstoneWireComparatorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockStateExtension;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings({"AddedMixinMembersNamePattern", "MissingUnique", "unused"})
@Mixin(RedstoneWireComparatorBlock.class)
public abstract class NeoRedstoneWireComparatorBlockMixin implements IBlockStateExtension {

    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (level instanceof Level lvl) {
            lvl.neighborChanged(state, pos, level.getBlockState(neighbor).getBlock(), neighbor, false);
        }
    }
}