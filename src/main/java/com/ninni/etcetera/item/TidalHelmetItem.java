package com.ninni.etcetera.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class TidalHelmetItem extends ArmorItem {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/models/armor/tidal_layer_1.png");
    public static final ResourceLocation ACTIVATED_TEXTURE = new ResourceLocation(MOD_ID, "textures/models/armor/active_tidal_layer_1.png");

    public TidalHelmetItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (entity instanceof LivingEntity livingEntity) {
            ResourceLocation texture = livingEntity.isEyeInFluid(FluidTags.WATER) && livingEntity.hasEffect(MobEffects.CONDUIT_POWER) ? ACTIVATED_TEXTURE : TEXTURE;
            return texture.toString();
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }
}
