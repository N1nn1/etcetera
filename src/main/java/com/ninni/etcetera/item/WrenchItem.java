package com.ninni.etcetera.item;

import com.ninni.etcetera.EtceteraTags;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;


public class WrenchItem extends Item {

    public WrenchItem(ToolMaterial toolMaterial, Settings settings) { super(settings); }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        this.use(player, world.getBlockState(blockPos), world, blockPos, !player.isSneaking(), context.getStack());
        return ActionResult.SUCCESS;
    }

    private void use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        Block block = state.getBlock();
        StateManager<Block, BlockState> stateManager = block.getStateManager();
        Collection<Property<?>> collection = stateManager.getProperties();
        String string = Registries.BLOCK.getId(block).toString();
        NbtCompound nbtCompound = stack.getOrCreateSubNbt("DebugProperty");
        Property<?> property = stateManager.getProperty(nbtCompound.getString(string));
        if (state.isIn(EtceteraTags.NON_MODIFIABLE)) {
            player.getItemCooldownManager().set(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL, 1, 1);
            player.sendMessage(Text.translatable(this.getTranslationKey() + ".block.invalid", block.getName()), true);
        } else if (collection.contains(Properties.AXIS)
            || collection.contains(Properties.HORIZONTAL_FACING)
            || collection.contains(Properties.HORIZONTAL_AXIS)
            || collection.contains(Properties.STAIR_SHAPE)
            || collection.contains(Properties.FACING))
        {
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (update) {
                if (property == null
                    || property == Properties.WATERLOGGED
                    || property == Properties.LIT
                    || property == Properties.POWERED
                    || property == Properties.ATTACHMENT
                    || property == Properties.HAS_BOOK
                    || property == Properties.OPEN
                    || property == Properties.HONEY_LEVEL)
                    property = collection.iterator().next();
                BlockState blockState = cycle(state, property, false);
                world.setBlockState(pos, blockState, 18);
                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_MODIFY, 1, 1);
                if (!player.getAbilities().creativeMode) stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
                player.playSound(world.getBlockState(pos).getSoundGroup().getPlaceSound(), 1, 1);
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
            } else {
                property = cycle(collection, property, false);
                String string3 = property.getName();
                nbtCompound.putString(string, string3);
                player.playSound(EtceteraSoundEvents.ITEM_WRENCH_SELECT, 1, 1);
                if (property == Properties.WATERLOGGED
                    || property == Properties.LIT
                    || property == Properties.POWERED
                    || property == Properties.ATTACHMENT
                    || property == Properties.HAS_BOOK
                    || property == Properties.OPEN
                    || property == Properties.HONEY_LEVEL) {
                    player.sendMessage(Text.translatable(this.getTranslationKey() + ".invalid", property.getName()), true);
                }
                else player.sendMessage(Text.translatable(this.getTranslationKey() + ".select", property.getName()), true);
            }
        } else {
            player.getItemCooldownManager().set(this, 15);
            player.playSound(EtceteraSoundEvents.ITEM_WRENCH_FAIL, 1, 1);
            player.sendMessage(Text.translatable(this.getTranslationKey() + ".block.invalid"), true);
        }
    }

    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) { return state.with(property, cycle(property.getValues(), state.get(property), inverse)); }
    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) { return inverse ? Util.previous(elements, current) : Util.next(elements, current); }

}
