package com.ninni.etcetera.item;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;


public class SextantItem extends Item {
    public SextantItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.getLightLevel(LightType.SKY, player.getBlockPos()) > 0) {
            player.sendMessage(Text.translatable(this.getTranslationKey() + ".actionbar", player.getBlockX(), player.getBlockZ()), true);
            player.getItemCooldownManager().set(this, 20 * 2);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            player.playSound(EtceteraSoundEvents.ITEM_SEXTANT_SUCCESS, 2, 1);
        } else {
            player.getItemCooldownManager().set(this, 20);
            player.playSound(EtceteraSoundEvents.ITEM_SEXTANT_FAIL, 2, 1);
        }
        player.setCurrentHand(hand);
        return TypedActionResult.consume(player.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20 * 8;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }
}
