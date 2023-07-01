package com.ninni.etcetera.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
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

public class TransformingItem extends ToolItem {
    private final TagKey<Block> blockTagKey;
    private final SoundEvent sound;
    private final Supplier<Map<Block, Block>> transformations;

    public TransformingItem(ToolMaterial material, Supplier<Map<Block, Block>> transformations, Settings settings, SoundEvent sound, TagKey<Block> blockTagKey) {
        super(material, settings);
        this.transformations = transformations;
        this.sound = sound;
        this.blockTagKey = blockTagKey;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (world.isClient && state.isIn(blockTagKey)) {
            player.playSound(this.sound, 0.75F, 1);
            return ActionResult.SUCCESS;
        }

        return this.transformations.get()
                                   .entrySet()
                                   .stream()
                                   .filter(entry -> state.isOf(entry.getKey()))
                                   .findFirst()
                                   .map(entry -> {
                                       this.transform(world, state, entry.getValue(), pos, player, context.getStack());
                                       return ActionResult.SUCCESS;
                                   })
                                   .orElseGet(() -> super.useOnBlock(context));
    }

    @SuppressWarnings("unchecked")
    public void transform(World world, BlockState oldState, Block block, BlockPos pos, PlayerEntity player, ItemStack stack) {
        BlockState state = block.getDefaultState();
        for (Property<?> property : oldState.getProperties()) {
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
