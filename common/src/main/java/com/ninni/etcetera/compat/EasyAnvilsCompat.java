package com.ninni.etcetera.compat;

import fuzs.easyanvils.EasyAnvils;
import fuzs.easyanvils.network.S2COpenNameTagEditorMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EasyAnvilsCompat {

    public static void openItemLabelScreen(Player player, InteractionHand hand, ItemStack stack) {
        if (player instanceof ServerPlayer serverPlayer) {
            EasyAnvils.NETWORK.sendTo(serverPlayer, new S2COpenNameTagEditorMessage(hand, stack.getHoverName()).toClientboundMessage());
        }
    }
}
