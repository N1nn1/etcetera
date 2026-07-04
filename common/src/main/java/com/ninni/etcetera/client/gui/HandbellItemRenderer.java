package com.ninni.etcetera.client.gui;

import com.ninni.etcetera.item.HandbellItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static com.ninni.etcetera.Constants.MOD_ID;

public class HandbellItemRenderer {
    public static final ModelResourceLocation INVENTORY_MODEL_ID = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "handbell"),
            "inventory"
    );

    public static final ModelResourceLocation INVENTORY_IN_HAND_MODEL_ID = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/handbell_in_hand"),
            "standalone"
    );

    public static boolean isInventory(ItemStack stack, ItemDisplayContext mode) {
        return (mode == ItemDisplayContext.GUI || mode == ItemDisplayContext.GROUND || mode == ItemDisplayContext.FIXED) && stack.getItem() instanceof HandbellItem;
    }
}