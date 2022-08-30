package com.ninni.etcetera.mixin.client;

import com.ninni.etcetera.client.gui.SextantItemRenderer;
import com.ninni.etcetera.item.SextantItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * I don't even know how this thing works but thanks anyway andante
 */

@Environment(EnvType.CLIENT)
@Mixin(ItemModels.class)
public abstract class ItemModelsMixin {
    @Shadow
    public abstract BakedModelManager getModelManager();

    @Inject(
        method = "getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onGetModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.getItem() instanceof SextantItem) {
            BakedModelManager models = this.getModelManager();
            cir.setReturnValue(models.getModel(SextantItemRenderer.INVENTORY_IN_HAND_MODEL_ID));
        }
    }
}
