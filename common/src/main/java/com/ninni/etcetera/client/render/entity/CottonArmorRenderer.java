package com.ninni.etcetera.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.etcetera.client.model.CottonArmorModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

import static com.ninni.etcetera.Constants.MOD_ID;

public class CottonArmorRenderer {
    private static final BiFunction<String, String, ResourceLocation> BI_FUNCTION = (s, s2) -> ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/models/armor/cotton_" + s + "_" + s2 + ".png");
    private CottonArmorModel<LivingEntity> armorModel;

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (this.armorModel == null) {
            this.armorModel = new CottonArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(EtceteraEntityModelLayers.PLAYER_COTTON));
        }
        contextModel.copyPropertiesTo(this.armorModel);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(getTexture(stack.getItem())));
        this.armorModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    public ResourceLocation getTexture(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        String type = name.contains("sweater") || name.contains("robe") ? "sweater" : "hat";
        String traderType = name.contains("hood") ? "hood" : "robe";
        String removal = name.replace("cotton_", "").replace("_" + (name.contains("trader") ? traderType : type), "");
        return BI_FUNCTION.apply(type, removal);
    }
}