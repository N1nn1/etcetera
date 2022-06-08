package com.ninni.etcetera.item;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public interface EtceteraItemTags {
    TagKey<Item> CONCRETE = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "concrete"));
    TagKey<Item> ALL_CONCRETE = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "all_concrete"));
    TagKey<Item> ALL_TERRACOTTA = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "all_terracotta"));
}
