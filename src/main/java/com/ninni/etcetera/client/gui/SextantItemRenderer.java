package com.ninni.etcetera.client.gui;

import com.ninni.etcetera.item.SextantItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.*;

/**
 * Sorta copied from andante
 */

@Environment(EnvType.CLIENT)
public class SextantItemRenderer {
    public static final ModelIdentifier INVENTORY_MODEL_ID = new ModelIdentifier(new Identifier(MOD_ID, "sextant"), "inventory");
    public static final ModelIdentifier INVENTORY_IN_HAND_MODEL_ID = new ModelIdentifier(new Identifier(MOD_ID, "sextant_in_hand"), "inventory");

    public static BakedModel modifyRenderItem(ItemStack stack, ModelTransformation.Mode mode) {
        boolean isInventory = mode == ModelTransformation.Mode.GUI || mode == ModelTransformation.Mode.GROUND || mode == ModelTransformation.Mode.FIXED;
        if (isInventory && stack.getItem() instanceof SextantItem) {
            BakedModelManager models = MinecraftClient.getInstance().getBakedModelManager();
            return models.getModel(INVENTORY_MODEL_ID);
        }

        return null;
    }
}
