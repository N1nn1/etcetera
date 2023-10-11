package com.ninni.etcetera.block;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraProperties;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RubberCauldronBlock extends AbstractCauldronBlock {
    public static final IntProperty SOLID = EtceteraProperties.SOLID;

    public RubberCauldronBlock(AbstractBlock.Settings settings) {
        super(settings, CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState().with(SOLID, 1));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty() && state.get(SOLID) == 3) {
            player.giveItemStack(EtceteraItems.RUBBER_BLOCK.getDefaultStack());
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
            int i = state.get(SOLID);
            BlockState state2 = world.getBlockState(pos.up());
            if (i < 3 && state2.isOf(EtceteraBlocks.COPPER_TAP) && state2.get(CoppertapBlock.POWERED)) {
                if (random.nextInt(7) == 0) {
                    world.setBlockState(pos, state.with(SOLID, i + 1), 2);
                }
            }
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return Items.CAULDRON.getDefaultStack();
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SOLID);
    }

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { super.appendProperties(builder.add(SOLID)); }
}
