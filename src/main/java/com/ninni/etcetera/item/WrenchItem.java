package com.ninni.etcetera.item;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;


public class WrenchItem extends Item {

    public WrenchItem(Tier toolMaterial, Properties settings) { super(settings); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        this.use(player, world.getBlockState(blockPos), world, blockPos, !player.isShiftKeyDown(), context.getItemInHand());
        return InteractionResult.SUCCESS;
    }

    private void use(Player player, BlockState state, LevelAccessor world, BlockPos pos, boolean update, ItemStack stack) {
        Block block = state.getBlock();
        StateDefinition<Block, BlockState> stateManager = block.getStateDefinition();
        Collection<Property<?>> collection = stateManager.getProperties();
        String string = BuiltInRegistries.BLOCK.getKey(block).toString();
        CompoundTag nbtCompound = stack.getOrCreateTagElement("DebugProperty");
        Property<?> property = stateManager.getProperty(nbtCompound.getString(string));
        if (state.is(EtceteraTags.NON_MODIFIABLE)) {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL.get(), 1, 1);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".block.invalid", block.getName()), true);
        } else if (collection.contains(BlockStateProperties.AXIS)
            || collection.contains(BlockStateProperties.HORIZONTAL_FACING)
            || collection.contains(BlockStateProperties.HORIZONTAL_AXIS)
            || collection.contains(BlockStateProperties.STAIRS_SHAPE)
            || collection.contains(BlockStateProperties.FACING))
        {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (update) {
                if (property == null
                    || property == BlockStateProperties.WATERLOGGED
                    || property == BlockStateProperties.LIT
                    || property == BlockStateProperties.POWERED
                    || property == BlockStateProperties.BELL_ATTACHMENT
                    || property == BlockStateProperties.HAS_BOOK
                    || property == BlockStateProperties.OPEN
                    || property == BlockStateProperties.LEVEL_HONEY)
                    property = collection.iterator().next();
                BlockState blockState = cycle(state, property, false);
                world.setBlock(pos, blockState, 18);
                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_MODIFY.get(), 1, 1);
                if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                player.playSound(world.getBlockState(pos).getSoundType().getPlaceSound(), 1, 1);
                world.levelEvent(2001, pos, Block.getId(state));
            } else {
                property = cycle(collection, property, false);
                String string3 = property.getName();
                nbtCompound.putString(string, string3);
                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_SELECT.get(), 1, 1);
                if (property == BlockStateProperties.WATERLOGGED
                    || property == BlockStateProperties.LIT
                    || property == BlockStateProperties.POWERED
                    || property == BlockStateProperties.BELL_ATTACHMENT
                    || property == BlockStateProperties.HAS_BOOK
                    || property == BlockStateProperties.OPEN
                    || property == BlockStateProperties.LEVEL_HONEY) {
                    player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".invalid", property.getName()), true);
                }
                else player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".select", property.getName()), true);
            }
        } else {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL.get(), 1, 1);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".block.invalid"), true);
        }
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) { return state.setValue(property, cycle(property.getPossibleValues(), state.getValue(property), inverse)); }
    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) { return inverse ? Util.findPreviousInIterable(elements, current) : Util.findNextInIterable(elements, current); }

}
