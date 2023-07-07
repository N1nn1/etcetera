package com.ninni.etcetera.client.renderer.entity;

import com.google.common.collect.Maps;
import com.ninni.etcetera.client.model.SweaterModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.registry.EtceteraItems;
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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class CottonArmorRenderer implements ArmorRenderer {
    private SweaterModel<LivingEntity> armorModel;
    private static final Map<DyeColor, Identifier> TEXTURES = Util.make(Maps.newHashMap(), map -> {
       for (DyeColor dyeColor : DyeColor.values()) {
           map.put(dyeColor, new Identifier(MOD_ID, "textures/models/armor/sweater_" + dyeColor.getName() + ".png"));
       }
    });
    public static final Identifier TRADER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/trader_robe.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new SweaterModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EtceteraEntityModelLayers.PLAYER_SWEATER));
        contextModel.copyBipedStateTo(this.armorModel);

        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, getTexture(stack.getItem()));
    }

    public Identifier getTexture(Item item) {
        if (item.equals(EtceteraItems.TRADER_ROBE)) {
            return TRADER_TEXTURE;
        } else {
            return TEXTURES.get(DyeColor.byName(Registries.ITEM.getId(item).getPath().replace("_sweater", ""), null));
        }
    }
}
