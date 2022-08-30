package com.ninni.etcetera.item;

import com.ninni.etcetera.crafting.ChisellingManager;
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

public class ChiselItem extends Item {
    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Map<Block, Block> chisel = ChisellingManager.getChiselling();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        for (Block block : chisel.keySet()) {
            BlockState chiselledState = chisel.get(block).getDefaultState();

            if (world.getBlockState(pos).isOf(block)) {
                this.chisel(world, chiselledState, pos, player, context.getStack());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public void chisel(World world, BlockState state, BlockPos pos, PlayerEntity player, ItemStack stack) {
        world.setBlockState(pos, state);
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.getAbilities().creativeMode) stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        player.playSound(world.getBlockState(pos).getSoundGroup().getPlaceSound(), 1, 1);
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
    }
}
