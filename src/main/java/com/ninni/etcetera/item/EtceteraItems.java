package com.ninni.etcetera.item;

import com.ninni.etcetera.block.EtceteraBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public class EtceteraItems {

    public static final Item BOUQUET = register("bouquet", new BlockItem(EtceteraBlocks.BOUQUET, new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }
}
