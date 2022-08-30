package com.ninni.etcetera.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Map;
import java.util.function.Supplier;

public class TransformingItem extends Item {
    private final Supplier<Map<Block, Block>> transformations;

    public TransformingItem(Supplier<Map<Block, Block>> transformations, Settings settings) {
        super(settings);
        this.transformations = transformations;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) return super.useOnBlock(context);

        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        return this.transformations.get()
                                   .entrySet()
                                   .stream()
                                   .filter(entry -> state.isOf(entry.getKey()))
                                   .findFirst()
                                   .map(entry -> {
                                       PlayerEntity player = context.getPlayer();
                                       this.transform(world, state, entry.getValue(), pos, player, context.getStack());
                                       return ActionResult.SUCCESS;
                                   })
                                   .orElseGet(() -> super.useOnBlock(context));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void transform(World world, BlockState oldState, Block block, BlockPos pos, PlayerEntity player, ItemStack stack) {
        BlockState state = block.getDefaultState();
        for (Property<?> property : oldState.getProperties()) {
            // 😭
            if (state.contains(property)) {
                if (property instanceof BooleanProperty bool) {
                    state = state.with(bool, oldState.get(bool));
                } else if (property instanceof DirectionProperty direction) {
                    state = state.with(direction, oldState.get(direction));
                } else if (property instanceof EnumProperty enu) {
                    state = state.with(enu, oldState.get(enu));
                } else if (property instanceof IntProperty i) {
                    state = state.with(i, state.get(i));
                }
            }
        }

        world.setBlockState(pos, state);
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.getAbilities().creativeMode) stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        player.playSound(world.getBlockState(pos).getSoundGroup().getPlaceSound(), 1, 1);
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
    }
}
