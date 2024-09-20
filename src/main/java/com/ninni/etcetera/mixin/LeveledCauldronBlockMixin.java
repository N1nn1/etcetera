package com.ninni.etcetera.mixin;

import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;

@Mixin(LeveledCauldronBlock.class)
public abstract class LeveledCauldronBlockMixin extends AbstractCauldronBlock {

    public LeveledCauldronBlockMixin(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, behaviorMap);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        ItemStack stack = player.getStackInHand(hand);

        if (world.getBlockState(pos).isOf(Blocks.WATER_CAULDRON) && stack.hasNbt() && (stack.getNbt().contains("Label1") || stack.getNbt().contains("Label2") || stack.getNbt().contains("Label") || stack.getNbt().contains("Label4"))) {

            ItemStack itemStack2 = stack.copy();
            NbtCompound tag = stack.getOrCreateNbt();
            tag.remove("Label1");
            tag.remove("Label2");
            tag.remove("Label3");
            tag.remove("Label4");
            itemStack2.setNbt(tag);
            player.setStackInHand(hand, itemStack2);
            LeveledCauldronBlock.decrementFluidLevel(world.getBlockState(pos), world, pos);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
