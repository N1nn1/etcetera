package com.ninni.etcetera.item;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WrenchItem extends Item {

    public WrenchItem(Tier toolMaterial, Properties properties) {
        super(properties);
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
        return state.setValue(property, cycle(property.getPossibleValues(), state.getValue(property), inverse));
    }

    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        List<T> list = new ArrayList<>();
        elements.forEach(list::add);
        int i = list.indexOf(current);
        if (i == -1) return list.getFirst();
        int j = inverse ? (i - 1) : (i + 1);
        if (j < 0) j = list.size() - 1;
        if (j >= list.size()) j = 0;
        return list.get(j);
    }

    /**
     * Helper method to determine if a property should NOT be modified by the wrench.
     * This prevents duplication glitches, free resources, and logic breaks.
     */
    private boolean isInvalidProperty(@Nullable Property<?> property) {
        if (property == null) return true;

        String name = property.getName();
        if (name.equals("age") || name.equals("level")) {
            return true;
        }

        return property == BlockStateProperties.WATERLOGGED
                || property == BlockStateProperties.LIT
                || property == BlockStateProperties.POWERED
                || property == BlockStateProperties.ATTACH_FACE
                || property == BlockStateProperties.HAS_BOOK
                || property == BlockStateProperties.OPEN
                || property == BlockStateProperties.LEVEL_HONEY
                || property == BlockStateProperties.FLOWER_AMOUNT
                || property == BlockStateProperties.CANDLES
                || property == BlockStateProperties.PICKLES
                || property == BlockStateProperties.EGGS
                || property == BlockStateProperties.HATCH
                || property == BlockStateProperties.BITES
                || property == BlockStateProperties.BERRIES
                || property == BlockStateProperties.OMINOUS
                || property == BlockStateProperties.VAULT_STATE
                || property == BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        if (player != null) {
            this.use(player, world.getBlockState(blockPos), world, blockPos, !player.isShiftKeyDown(), context.getItemInHand());
        }
        return InteractionResult.SUCCESS;
    }

    private void use(Player player, BlockState state, LevelAccessor world, BlockPos pos, boolean update, ItemStack stack) {
        Block block = state.getBlock();
        StateDefinition<Block, BlockState> stateDefinition = block.getStateDefinition();
        Collection<Property<?>> collection = stateDefinition.getProperties();
        String string = BuiltInRegistries.BLOCK.getKey(block).toString();

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbtCompound = customData.copyTag();

        Property<?> property = stateDefinition.getProperty(nbtCompound.getString(string));

        if (state.is(EtceteraTags.NON_MODIFIABLE)) {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL, 1, 1);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".block.invalid", block.getName()), true);
        } else if (collection.contains(BlockStateProperties.AXIS)
                || collection.contains(BlockStateProperties.HORIZONTAL_FACING)
                || collection.contains(BlockStateProperties.HORIZONTAL_AXIS)
                || collection.contains(BlockStateProperties.STAIRS_SHAPE)
                || collection.contains(BlockStateProperties.FACING)) {

            player.awardStat(Stats.ITEM_USED.get(this));

            if (update) {
                if (isInvalidProperty(property)) {
                    property = collection.iterator().next();
                }

                BlockState blockState = cycle(state, property, false);
                world.setBlock(pos, blockState, 18);
                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_MODIFY, 1, 1);

                if (!player.getAbilities().instabuild) {
                    stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                }

                player.playSound(world.getBlockState(pos).getSoundType().getPlaceSound(), 1, 1);
                world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
            } else {
                property = cycle(collection, property, false);
                String string3 = property.getName();

                nbtCompound.putString(string, string3);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbtCompound));

                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_SELECT, 1, 1);

                if (isInvalidProperty(property)) {
                    player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".invalid", property.getName()), true);
                } else {
                    player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".select", property.getName()), true);
                }
            }
        } else {
            player.getCooldowns().addCooldown(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL, 1, 1);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".block.invalid"), true);
        }
    }
}