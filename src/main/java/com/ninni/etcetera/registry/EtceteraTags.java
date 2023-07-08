package com.ninni.etcetera.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public interface EtceteraTags {
    TagKey<Item> CONCRETE = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "concrete"));
    TagKey<Item> ALL_CONCRETE = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_concrete"));
    TagKey<Item> ALL_TERRACOTTA = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_terracotta"));
    TagKey<Item> GLAZED_TERRACOTTA = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "glazed_terracotta"));
    TagKey<Item> ALL_GLAZED_TERRACOTTA = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_glazed_terracotta"));
    TagKey<Item> ALL_WOOL = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_wool"));
    TagKey<Item> ALL_GLASS_BLOCKS = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_glass_blocks"));
    TagKey<Item> ALL_GLASS_PANES = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_glass_panes"));
    TagKey<Item> SWEATERS = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "sweaters"));
    TagKey<Item> HATS = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "hats"));
    TagKey<Item> VILLAGER_CAN_PICKUP = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "villager_can_pickup"));

    TagKey<Block> NON_MODIFIABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "non_modifiable"));
    TagKey<Block> CHISELLABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "chisellable"));
    TagKey<Block> HAMMERABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "hammerable"));
    TagKey<Block> OFFSET_REMOVER = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "offset_remover"));
}