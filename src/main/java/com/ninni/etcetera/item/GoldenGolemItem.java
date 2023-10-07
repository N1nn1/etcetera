package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.GoldenGolemItemEntity;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldenGolemItem extends Item {

    public GoldenGolemItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);


        if (!world.isClient) {
            if (itemStack.hasNbt() && itemStack.getNbt().contains("Broken") && itemStack.getNbt().getBoolean("Broken")) {
                return TypedActionResult.fail(itemStack);
            } else {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_THROW, SoundCategory.PLAYERS, 0.7f, 1f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                GoldenGolemItemEntity goldenGolemItem = new GoldenGolemItemEntity(world, user);
                goldenGolemItem.setItem(itemStack);
                goldenGolemItem.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                world.spawnEntity(goldenGolemItem);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                if (!user.getAbilities().creativeMode) itemStack.decrement(1);

                return TypedActionResult.success(itemStack);
            }
        }

        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        NbtCompound nbt = stack.getNbt();

        if (stack.hasNbt() && nbt.contains("HealingAmount")) tooltip.add(Text.translatable("item.etcetera.golden_golem.healing_amount", nbt.getInt("HealingAmount")).formatted(Formatting.YELLOW));
        if (stack.hasNbt() && nbt.contains("HealingCooldown") && nbt.getInt("HealingCooldown") > 0) tooltip.add(Text.translatable("item.etcetera.golden_golem.cooldown").formatted(Formatting.GRAY));
    }
}
