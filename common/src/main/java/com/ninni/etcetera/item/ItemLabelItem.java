package com.ninni.etcetera.item;

import com.ninni.etcetera.compat.EasyAnvilsCompat;
import com.ninni.etcetera.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemLabelItem extends Item {

    public ItemLabelItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (Services.PLATFORM.isModLoaded("easyanvils") && player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                EasyAnvilsCompat.openItemLabelScreen(player, hand, stack);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        return super.use(level, player, hand);
    }
}
