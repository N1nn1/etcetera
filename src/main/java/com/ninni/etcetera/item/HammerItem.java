package com.ninni.etcetera.item;

import com.ninni.etcetera.crafting.HammeringManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Map;

public class HammerItem extends Item {
    public HammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Map<Block, Block> hammer = HammeringManager.getHammering();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        for (Block block : hammer.keySet()) {
            BlockState hammeredState = hammer.get(block).getDefaultState();

            if (world.getBlockState(pos).isOf(block)) {
                this.hammer(world, hammeredState, pos, player, context.getStack());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public void hammer(World world, BlockState state, BlockPos pos, PlayerEntity player, ItemStack stack) {
        world.setBlockState(pos, state);
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.getAbilities().creativeMode) stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        player.playSound(world.getBlockState(pos).getSoundGroup().getPlaceSound(), 1, 1);
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
    }
}
