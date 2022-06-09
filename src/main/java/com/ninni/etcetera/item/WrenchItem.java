package com.ninni.etcetera.item;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;


public class WrenchItem extends Item {

    public WrenchItem(Settings settings) { super(settings); }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (!player.getAbilities().creativeMode) context.getStack().damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        this.use(player, world.getBlockState(blockPos), world, blockPos, !player.isSneaking(), context.getStack());
        return ActionResult.FAIL;
    }

    private void use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        Block block = state.getBlock();
        StateManager<Block, BlockState> stateManager = block.getStateManager();
        Collection<Property<?>> collection = stateManager.getProperties();
        String string = Registry.BLOCK.getId(block).toString();
        NbtCompound nbtCompound = stack.getOrCreateSubNbt("DebugProperty");
        Property<?> property = stateManager.getProperty(nbtCompound.getString(string));

        if (collection.contains(Properties.AXIS)
            || collection.contains(Properties.FACING)
            || collection.contains(Properties.HORIZONTAL_FACING)
            || collection.contains(Properties.HORIZONTAL_AXIS)
            || collection.contains(Properties.STAIR_SHAPE))
        {
            player.playSound(EtceteraSoundEvents.ITEM_SEXTANT_SUCCESS, 2, 1);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            player.getItemCooldownManager().set(this, 5);

            if (update) {
                if (property == null
                    || property == Properties.WATERLOGGED)
                    property = collection.iterator().next();
                BlockState blockState = cycle(state, property, false);
                world.setBlockState(pos, blockState, 18);
                player.playSound(world.getBlockState(pos).getSoundGroup().getPlaceSound(), 2, 1);
            } else {
                property = cycle(collection, property, false);
                String string3 = property.getName();
                nbtCompound.putString(string, string3);
                if (property == Properties.WATERLOGGED) {
                    player.sendMessage(Text.translatable(this.getTranslationKey() + ".invalid", property.getName()), true);
                }
                else player.sendMessage(Text.translatable(this.getTranslationKey() + ".select", property.getName()), true);
            }
        } else player.sendMessage(Text.translatable(this.getTranslationKey() + ".block.invalid", block.getName()), true);
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) { return state.with(property, cycle(property.getValues(), state.get(property), inverse)); }
    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) { return inverse ? Util.previous(elements, current) : Util.next(elements, current); }

}
