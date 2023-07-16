package com.ninni.etcetera.client.gui;

import com.ninni.etcetera.item.HandbellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class HandbellItemRenderer {
    public static final ModelResourceLocation INVENTORY_MODEL_ID = new ModelResourceLocation(new ResourceLocation(MOD_ID, "handbell"), "inventory");
    public static final ModelResourceLocation INVENTORY_IN_HAND_MODEL_ID = new ModelResourceLocation(new ResourceLocation(MOD_ID, "handbell_in_hand"), "inventory");

    public static BakedModel modifyRenderItem(ItemStack stack, ItemDisplayContext mode) {
        if (isInventory(stack, mode)) {
            ModelManager models = Minecraft.getInstance().getModelManager();
            return models.getModel(INVENTORY_MODEL_ID);
        }
        return null;
    }

    public static boolean isInventory(ItemStack stack, ItemDisplayContext mode) {
        return (mode == ItemDisplayContext.GUI || mode == ItemDisplayContext.GROUND || mode == ItemDisplayContext.FIXED) && stack.getItem() instanceof HandbellItem;
    }
}
