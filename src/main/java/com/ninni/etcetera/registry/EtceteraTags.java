package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface EtceteraTags {
    TagKey<Item> CONCRETE = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "concrete"));
    TagKey<Item> ALL_CONCRETE = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_concrete"));
    TagKey<Item> ALL_TERRACOTTA = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_terracotta"));
    TagKey<Item> GLAZED_TERRACOTTA = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "glazed_terracotta"));
    TagKey<Item> ALL_GLAZED_TERRACOTTA = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_glazed_terracotta"));
    TagKey<Item> ALL_WOOL = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_wool"));
    TagKey<Item> ALL_GLASS_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_glass_blocks"));
    TagKey<Item> ALL_GLASS_PANES = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "all_glass_panes"));
    TagKey<Item> SWEATERS = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "sweaters"));
    TagKey<Item> HATS = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "hats"));
    TagKey<Item> VILLAGER_CAN_PICKUP = TagKey.create(Registries.ITEM, new ResourceLocation(Etcetera.MOD_ID, "villager_can_pickup"));

    TagKey<Block> NON_MODIFIABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Etcetera.MOD_ID, "non_modifiable"));
    TagKey<Block> CHISELLABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Etcetera.MOD_ID, "chisellable"));
    TagKey<Block> HAMMERABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Etcetera.MOD_ID, "hammerable"));
    TagKey<Block> OFFSET_REMOVER = TagKey.create(Registries.BLOCK, new ResourceLocation(Etcetera.MOD_ID, "offset_remover"));
}