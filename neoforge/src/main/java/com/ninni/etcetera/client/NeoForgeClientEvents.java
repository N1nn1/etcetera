package com.ninni.etcetera.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.etcetera.Constants;
import com.ninni.etcetera.block.RedstoneWiresBlock;
import com.ninni.etcetera.client.gui.HandbellItemRenderer;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreen;
import com.ninni.etcetera.client.model.*;
import com.ninni.etcetera.client.particles.GoldenParticle;
import com.ninni.etcetera.client.particles.RubberParticle;
import com.ninni.etcetera.client.render.block.entity.ItemStandBlockEntityRenderer;
import com.ninni.etcetera.client.render.entity.*;
import com.ninni.etcetera.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeClientEvents {

    @SubscribeEvent
    public static void onRegisterModels(ModelEvent.RegisterAdditional event) {
        event.register(HandbellItemRenderer.INVENTORY_IN_HAND_MODEL_ID);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EtceteraBlockEntityType.ITEM_STAND.get(), ItemStandBlockEntityRenderer::new);

        event.registerEntityRenderer(EtceteraEntityType.TURTLE_RAFT.get(), TurtleRaftRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.CHAPPLE.get(), ChappleRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.EGGPLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.WEAVER.get(), WeaverRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.COBWEB.get(), CobwebProjectileEntityRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.GOLDEN_GOLEM.get(), GoldenGolemRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.THROWN_GOLDEN_GOLEM.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.RUBBER_CHICKEN.get(), RubberChickenRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EtceteraEntityModelLayers.PLAYER_COTTON, CottonArmorModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.RUBBER_CHICKEN, RubberChickenModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.GOLDEN_GOLEM, GoldenGolemModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.COBWEB, CobwebProjectileModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.TURTLE_RAFT, TurtleRaftModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.WEAVER, WeaverModel::getTexturedModelData);
        event.registerLayerDefinition(EtceteraEntityModelLayers.CHAPPLE, ChappleModel::getTexturedModelData);
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> RedstoneWiresBlock.getWireColor(state.getValue(RedstoneWiresBlock.POWER)), EtceteraBlocks.REDSTONE_WIRES.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex > 0) return -1;
            DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
            int rgb = dyedColor != null ? dyedColor.rgb() : 0x3fa442;
            return 0xFF000000 | rgb;
        }, EtceteraItems.TURTLE_RAFT.get());
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(EtceteraParticleTypes.GOLDEN_HEART, GoldenParticle.Factory::new);
        event.registerSpriteSet(EtceteraParticleTypes.GOLDEN_SHEEN, GoldenParticle.Factory::new);

        event.registerSpriteSet(EtceteraParticleTypes.DRIPPING_RUBBER, sprites -> (type, world, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            var drippingRubber = RubberParticle.createDrippingRubber(world, x, y, z, xSpeed, ySpeed, zSpeed);
            drippingRubber.pickSprite(sprites);
            return drippingRubber;
        });
        event.registerSpriteSet(EtceteraParticleTypes.FALLING_RUBBER, sprites -> (type, world, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            var fallingRubber = RubberParticle.createFallingRubber(world, x, y, z, xSpeed, ySpeed, zSpeed);
            fallingRubber.pickSprite(sprites);
            return fallingRubber;
        });
        event.registerSpriteSet(EtceteraParticleTypes.LANDING_RUBBER, sprites -> (type, world, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            var landingRubber = RubberParticle.createLandingRubber(world, x, y, z, xSpeed, ySpeed, zSpeed);
            landingRubber.pickSprite(sprites);
            return landingRubber;
        });
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(EtceteraScreenHandlerType.PRICKLY_CAN, PricklyCanScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(EtceteraItems.GOLDEN_GOLEM.get(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "broken"), (stack, world, entity, seed) -> {
                CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
                if (customData != null) {
                    CompoundTag tag = customData.copyTag();
                    if (tag.contains("Broken") && tag.getBoolean("Broken")) {
                        return 1.0f;
                    }
                }
                return 0.0f;
            });
        });
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions cottonArmor = new IClientItemExtensions() {
            private final CottonArmorRenderer renderer = new CottonArmorRenderer();
            private CottonArmorModel<LivingEntity> model;
            private ItemStack currentStack = ItemStack.EMPTY;

            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity entityLiving, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel<?> _default) {
                this.currentStack = itemStack;

                if (model == null) {
                    model = new CottonArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(EtceteraEntityModelLayers.PLAYER_COTTON)) {
                        @Override
                        public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
                            ResourceLocation texture = renderer.getTexture(currentStack.getItem());
                            VertexConsumer customConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.armorCutoutNoCull(texture));
                            super.renderToBuffer(poseStack, customConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
                        }
                    };
                }
                _default.copyPropertiesTo((HumanoidModel) model);
                return model;
            }
        };

        event.registerItem(cottonArmor,
                EtceteraItems.WHITE_SWEATER.get(), EtceteraItems.LIGHT_GRAY_SWEATER.get(),
                EtceteraItems.GRAY_SWEATER.get(), EtceteraItems.BLACK_SWEATER.get(),
                EtceteraItems.BROWN_SWEATER.get(), EtceteraItems.RED_SWEATER.get(),
                EtceteraItems.ORANGE_SWEATER.get(), EtceteraItems.YELLOW_SWEATER.get(),
                EtceteraItems.LIME_SWEATER.get(), EtceteraItems.GREEN_SWEATER.get(),
                EtceteraItems.CYAN_SWEATER.get(), EtceteraItems.LIGHT_BLUE_SWEATER.get(),
                EtceteraItems.BLUE_SWEATER.get(), EtceteraItems.PURPLE_SWEATER.get(),
                EtceteraItems.MAGENTA_SWEATER.get(), EtceteraItems.PINK_SWEATER.get(),
                EtceteraItems.WHITE_HAT.get(), EtceteraItems.LIGHT_GRAY_HAT.get(),
                EtceteraItems.GRAY_HAT.get(), EtceteraItems.BLACK_HAT.get(),
                EtceteraItems.BROWN_HAT.get(), EtceteraItems.RED_HAT.get(),
                EtceteraItems.ORANGE_HAT.get(), EtceteraItems.YELLOW_HAT.get(),
                EtceteraItems.LIME_HAT.get(), EtceteraItems.GREEN_HAT.get(),
                EtceteraItems.CYAN_HAT.get(), EtceteraItems.LIGHT_BLUE_HAT.get(),
                EtceteraItems.BLUE_HAT.get(), EtceteraItems.PURPLE_HAT.get(),
                EtceteraItems.MAGENTA_HAT.get(), EtceteraItems.PINK_HAT.get(),
                EtceteraItems.TRADER_ROBE.get(), EtceteraItems.TRADER_HOOD.get()
        );

        IClientItemExtensions tidalArmor = new IClientItemExtensions() {
            private HumanoidModel<LivingEntity> model;
            private LivingEntity currentEntity;

            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity entityLiving, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel<?> _default) {
                this.currentEntity = entityLiving;

                if (model == null) {
                    model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)) {
                        @Override
                        public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
                            ResourceLocation texture = currentEntity.isEyeInFluid(FluidTags.WATER) && currentEntity.hasEffect(MobEffects.CONDUIT_POWER)
                                    ? ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/models/armor/active_tidal_layer_1.png")
                                    : ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/models/armor/tidal_layer_1.png");

                            VertexConsumer customConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.armorCutoutNoCull(texture));
                            super.renderToBuffer(poseStack, customConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
                        }
                    };
                }
                _default.copyPropertiesTo((HumanoidModel) model);
                return model;
            }
        };
        event.registerItem(tidalArmor, EtceteraItems.TIDAL_HELMET.get());

        IClientItemExtensions silkArmor = new IClientItemExtensions() {
            private HumanoidModel<LivingEntity> model;

            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity entityLiving, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel<?> _default) {
                if (model == null) {
                    model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)) {
                        @Override
                        public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
                            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/models/armor/silk_layer_2.png");
                            VertexConsumer customConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.armorCutoutNoCull(texture));
                            super.renderToBuffer(poseStack, customConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
                        }
                    };
                }
                _default.copyPropertiesTo((HumanoidModel) model);
                return model;
            }
        };
        event.registerItem(silkArmor, EtceteraItems.SILKEN_SLACKS.get());

        IClientItemExtensions adventurerArmor = new IClientItemExtensions() {
            private HumanoidModel<LivingEntity> model;

            @Override
            @SuppressWarnings({"unchecked", "rawtypes"})
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity entityLiving, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel<?> _default) {
                if (model == null) {
                    model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)) {
                        @Override
                        public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
                            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/models/armor/adventurer_layer_1.png");
                            VertexConsumer customConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.armorCutoutNoCull(texture));
                            super.renderToBuffer(poseStack, customConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
                        }
                    };
                }
                _default.copyPropertiesTo((HumanoidModel) model);
                return model;
            }
        };
        event.registerItem(adventurerArmor, EtceteraItems.ADVENTURERS_BOOTS.get());
    }
}