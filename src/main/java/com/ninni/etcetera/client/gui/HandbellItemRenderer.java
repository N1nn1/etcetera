package com.ninni.etcetera.client.gui;

import com.ninni.etcetera.item.HandbellItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.*;

@Environment(EnvType.CLIENT)
public class HandbellItemRenderer {
    public static final ModelIdentifier INVENTORY_MODEL_ID = new ModelIdentifier(new Identifier(MOD_ID, "handbell"), "inventory");
    public static final ModelIdentifier INVENTORY_IN_HAND_MODEL_ID = new ModelIdentifier(new Identifier(MOD_ID, "handbell_in_hand"), "inventory");

    public static BakedModel modifyRenderItem(ItemStack stack, ModelTransformationMode mode) {
        boolean isInventory = isInventory(stack, mode);
        if (isInventory(stack, mode)) {
            BakedModelManager models = MinecraftClient.getInstance().getBakedModelManager();
            return models.getModel(INVENTORY_MODEL_ID);
        }
        return null;
    }

    public static boolean isInventory(ItemStack stack, ModelTransformationMode mode) {
        return (mode == ModelTransformationMode.GUI || mode == ModelTransformationMode.GROUND || mode == ModelTransformationMode.FIXED) && stack.getItem() instanceof HandbellItem;
    }
}
