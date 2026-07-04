package com.ninni.etcetera.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class TransformingItem extends TieredItem {
    private final TagKey<Block> blockTagKey;
    private final SoundEvent sound;
    private final Supplier<Map<Block, Block>> transformations;

    public TransformingItem(Tier material, Supplier<Map<Block, Block>> transformations, Properties properties, SoundEvent sound, TagKey<Block> blockTagKey) {
        super(material, properties);
        this.transformations = transformations;
        this.sound = sound;
        this.blockTagKey = blockTagKey;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (world.isClientSide && state.is(blockTagKey)) {
            player.playSound(this.sound, 0.75F, 1);
            return InteractionResult.SUCCESS;
        }

        return this.transformations.get()
                .entrySet()
                .stream()
                .filter(entry -> state.is(entry.getKey()))
                .findFirst()
                .map(entry -> {
                    this.transform(world, state, entry.getValue(), pos, player, context.getItemInHand());
                    return InteractionResult.SUCCESS;
                })
                .orElseGet(() -> super.useOn(context));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void transform(Level world, BlockState oldState, Block block, BlockPos pos, Player player, ItemStack stack) {
        BlockState state = block.defaultBlockState();
        for (Property property : oldState.getProperties()) {
            if (state.hasProperty(property)) {
                state = state.setValue(property, oldState.getValue(property));
            }
        }

        world.setBlock(pos, state, 3);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild)
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        player.playSound(world.getBlockState(pos).getSoundType().getPlaceSound(), 1, 1);
        world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
    }
}