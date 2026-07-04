package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.GoldenGolemItemEntity;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GoldenGolemItem extends Item {

    public GoldenGolemItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        if (!world.isClientSide) {
            CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

            if (customData.contains("Broken") && customData.copyTag().getBoolean("Broken")) {
                return InteractionResultHolder.fail(itemStack);
            } else {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_THROW, SoundSource.PLAYERS, 0.7f, 1f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                GoldenGolemItemEntity goldenGolemItem = new GoldenGolemItemEntity(world, user);
                goldenGolemItem.setItem(itemStack);
                goldenGolemItem.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0f, 1.5f, 1.0f);
                world.addFreshEntity(goldenGolemItem);
                user.awardStat(Stats.ITEM_USED.get(this));

                if (!user.getAbilities().instabuild) itemStack.shrink(1);

                return InteractionResultHolder.success(itemStack);
            }
        }

        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if (customData.contains("HealingAmount"))
            tooltip.add(Component.translatable("item.etcetera.golden_golem.healing_amount", customData.copyTag().getInt("HealingAmount")).withStyle(ChatFormatting.YELLOW));
        if (customData.contains("HealingCooldown") && customData.copyTag().getInt("HealingCooldown") > 0)
            tooltip.add(Component.translatable("item.etcetera.golden_golem.cooldown").withStyle(ChatFormatting.GRAY));
    }
}