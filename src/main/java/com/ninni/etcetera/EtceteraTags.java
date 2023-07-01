package com.ninni.etcetera;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import static com.ninni.etcetera.Etcetera.*;

public interface EtceteraTags {
    TagKey<Item> CONCRETE = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "concrete"));
    TagKey<Item> ALL_CONCRETE = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_concrete"));
    TagKey<Item> ALL_TERRACOTTA = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "all_terracotta"));
    TagKey<Item> SNAIL_TEMPT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "snail_tempt_items"));

    TagKey<Block> NON_MODIFIABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "non_modifiable"));
    TagKey<Block> CHISELLABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "chisellable"));
    TagKey<Block> HAMMERABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "hammerable"));
    TagKey<Block> OFFSET_REMOVER = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "offset_remover"));
    TagKey<Block> MUCUS_SOLIDIFIER = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "mucus_solidifier"));
    TagKey<Block> MUCUS_SOLIDIFICATION_SPREADER = TagKey.of(RegistryKeys.BLOCK, new Identifier(MOD_ID, "mucus_solidification_spreader"));

    TagKey<Biome> SNAIL_SPAWNS = TagKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "snail_spawns"));
}