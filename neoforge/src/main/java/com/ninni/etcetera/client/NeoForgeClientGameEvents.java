package com.ninni.etcetera.client;

import com.ninni.etcetera.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NeoForgeClientGameEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        int color = 0x959595;
        Integer darkAqua = ChatFormatting.DARK_AQUA.getColor();
        Integer darkPurple = ChatFormatting.DARK_PURPLE.getColor();

        switch (stack.getRarity()) {
            case COMMON -> color = 0x959595;
            case UNCOMMON -> color = 0xbb7d2b;
            case RARE -> color = darkAqua != null ? darkAqua : 0x55FFFF;
            case EPIC -> color = darkPurple != null ? darkPurple : 0xAA00AA;
        }
        Style style = Style.EMPTY.withColor(color).withItalic(true);

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            for (int row = 1; row < 5; row++) {
                if (tag.contains("Label" + row)) {
                    event.getToolTip().add(row, Component.literal(tag.getString("Label" + row)).setStyle(style));
                }
            }
        }
    }
}