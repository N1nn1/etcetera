package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.client.model.CottonArmorModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class CottonArmorRenderer implements ArmorRenderer {
    private CottonArmorModel<LivingEntity> armorModel;
    private static final BiFunction<String, String, Identifier> BI_FUNCTION = (s, s2) -> new Identifier(MOD_ID, "textures/models/armor/cotton_" + s + "_" + s2 + ".png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new CottonArmorModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EtceteraEntityModelLayers.PLAYER_COTTON));
        contextModel.copyBipedStateTo(this.armorModel);

        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, getTexture(stack.getItem()));
    }

    public Identifier getTexture(Item item) {
        String name = Registries.ITEM.getId(item).getPath();
        String type = name.contains("sweater") || name.contains("robe") ? "sweater" : "hat";
        String traderType = name.contains("hood") ? "hood" : "robe";
        String removal = name.replace("cotton_", "").replace("_" + (name.contains("trader") ? traderType : type), "");
        return BI_FUNCTION.apply(type, removal);
    }
}
