package com.ninni.etcetera.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

import static com.ninni.etcetera.Constants.MOD_ID;

public interface EtceteraTags {

    TagKey<Item> CONCRETE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "concrete"));
    TagKey<Item> ALL_CONCRETE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "all_concrete"));
    TagKey<Item> ALL_TERRACOTTA = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "all_terracotta"));
    TagKey<Item> GLAZED_TERRACOTTA = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "glazed_terracotta"));
    TagKey<Item> ALL_GLAZED_TERRACOTTA = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "all_glazed_terracotta"));
    TagKey<Item> ALL_WOOL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "all_wool"));
    TagKey<Item> SWEATERS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "sweaters"));
    TagKey<Item> HATS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "hats"));
    TagKey<Item> VILLAGER_CAN_PICKUP = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "villager_can_pickup"));

    TagKey<Block> NON_MODIFIABLE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "non_modifiable"));
    TagKey<Block> CHISELLABLE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "chisellable"));
    TagKey<Block> HAMMERABLE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "hammerable"));
    TagKey<Block> OFFSET_REMOVER = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "offset_remover"));

    TagKey<Block> TAP_LAVA = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_lava"));
    TagKey<Block> TAP_WATER = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_water"));
    TagKey<Block> TAP_HONEY = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_honey"));
    TagKey<Block> TAP_RUBBER = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_rubber"));
    TagKey<Block> TAP_CRYING_OBSIDIAN = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_crying_obsidian"));
    TagKey<Block> TAP_ALWAYS_PLACEABLE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "tap_always_placeable"));

    TagKey<PaintingVariant> ETCETERA_PAINTING_VARIANTS = TagKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(MOD_ID, "etcetera_painting_variants"));
}